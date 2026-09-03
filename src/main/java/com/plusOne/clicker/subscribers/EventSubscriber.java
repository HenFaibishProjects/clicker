package com.plusOne.clicker.subscribers;

import com.plusOne.clicker.repositories.BigQueryRepository;
import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.messaging.InMemoryPubSub;
import com.plusOne.clicker.metrics.MetricsAggregator;
import com.plusOne.clicker.redis.ProcessedEventRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EventSubscriber {

    private static final Logger log =
            LoggerFactory.getLogger(EventSubscriber.class);

    private final MetricsAggregator metricsAggregator;
    private final BigQueryRepository bigQueryRepository;
    private final InMemoryPubSub pubSub;
    private final ProcessedEventRepository processedEventRepository;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public EventSubscriber(
            MetricsAggregator metricsAggregator,
            BigQueryRepository bigQueryRepository,
            InMemoryPubSub pubSub,
            ProcessedEventRepository processedEventRepository) {

        this.metricsAggregator = metricsAggregator;
        this.bigQueryRepository = bigQueryRepository;
        this.pubSub = pubSub;
        this.processedEventRepository = processedEventRepository;
    }

    @PostConstruct
    public void start() {
        executor.submit(this::listen);
    }

    private void listen() {

        while (!Thread.currentThread().isInterrupted()) {

            try {
                AdEvent event = pubSub.consume();
                log.info(
                        "[component=EventSubscriber][action=consume] eventId={} type={}",
                        event.eventId(),
                        event.type()
                );
                processEvent(event);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void processEvent(AdEvent event) {

        boolean claimed =
                processedEventRepository.tryClaim(event.eventId());

        if (!claimed) {
            log.info(
                    "[component=EventSubscriber][action=ignoreDuplicate] eventId={}",
                    event.eventId()
            );
            return;
        }

        try {
            log.info(
                    "[component=EventSubscriber][action=processEvent] eventId={} type={}",
                    event.eventId(),
                    event.type()
            );

            bigQueryRepository.save(event);
            metricsAggregator.aggregate(event);

        } catch (Exception e) {

            processedEventRepository.release(event.eventId());

            log.error(
                    "[component=EventSubscriber][action=processEventFailed] eventId={} type={}",
                    event.eventId(),
                    event.type(),
                    e
            );
        }
    }

    @PreDestroy
    public void stop() {
        executor.shutdownNow();
    }
}