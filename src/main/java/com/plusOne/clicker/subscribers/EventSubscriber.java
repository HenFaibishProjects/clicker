package com.plusOne.clicker.subscribers;

import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.messaging.InMemoryPubSub;
import com.plusOne.clicker.metrics.MetricsAggregator;
import com.plusOne.clicker.redis.RedisStore;
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
    private final InMemoryPubSub pubSub;
    private final RedisStore redisStore;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public EventSubscriber(MetricsAggregator metricsAggregator, InMemoryPubSub pubSub, RedisStore redisStore) {
        this.metricsAggregator = metricsAggregator;
        this.pubSub = pubSub;
        this.redisStore = redisStore;
    }

    @PostConstruct
    public void start() {
        executor.submit(this::listen);
    }

    private void listen() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                AdEvent event = pubSub.consume();
                boolean isNewEvent = redisStore.markIfNew(event.eventId());

                if (!isNewEvent) {
                    log.info(
                            "[component=EventSubscriber][action=ignoreDuplicate] eventId={}",
                            event.eventId()
                    );
                    continue;
                }

                log.info(
                        "[component=EventSubscriber][action=receiveEvent] eventId={} type={}",
                        event.eventId(),
                        event.type()
                );

            metricsAggregator.aggregate(event);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @PreDestroy
    public void stop() {
        executor.shutdownNow();
    }
}
