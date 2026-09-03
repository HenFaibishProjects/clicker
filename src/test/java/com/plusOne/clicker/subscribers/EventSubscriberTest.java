package com.plusOne.clicker.subscribers;

import com.plusOne.clicker.bigquery.BigQueryRepository;
import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.domain.EventType;
import com.plusOne.clicker.messaging.InMemoryPubSub;
import com.plusOne.clicker.metrics.MetricsAggregator;
import com.plusOne.clicker.redis.ProcessedEventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventSubscriberTest {

    private final MetricsAggregator metricsAggregator = mock(MetricsAggregator.class);
    private final BigQueryRepository bigQueryRepository = mock(BigQueryRepository.class);
    private final InMemoryPubSub pubSub = new InMemoryPubSub();
    private final ProcessedEventRepository processedEventRepository = mock(ProcessedEventRepository.class);
    private EventSubscriber subscriber;

    @BeforeEach
    void startSubscriber() {
        subscriber = new EventSubscriber(
                metricsAggregator,
                bigQueryRepository,
                pubSub,
                processedEventRepository
        );
        subscriber.start();
    }

    @AfterEach
    void stopSubscriber() {
        subscriber.stop();
    }

    @Test
    void newEventIsSavedAndAggregated() {
        AdEvent event = event("event-1");
        when(processedEventRepository.tryClaim(event.eventId())).thenReturn(true);

        pubSub.publish(event);

        verify(bigQueryRepository, timeout(1_000)).save(event);
        verify(metricsAggregator, timeout(1_000)).aggregate(event);
    }

    @Test
    void duplicateEventIsIgnored() {
        AdEvent event = event("event-1");
        when(processedEventRepository.tryClaim(event.eventId())).thenReturn(false);

        pubSub.publish(event);

        verify(processedEventRepository, timeout(1_000)).tryClaim(event.eventId());
        verify(bigQueryRepository, never()).save(event);
        verify(metricsAggregator, never()).aggregate(event);
    }

    @Test
    void processingFailureReleasesClaimAndDoesNotStopFutureProcessing() {
        AdEvent failedEvent = event("event-1");
        AdEvent nextEvent = event("event-2");
        when(processedEventRepository.tryClaim(failedEvent.eventId())).thenReturn(true);
        when(processedEventRepository.tryClaim(nextEvent.eventId())).thenReturn(true);
        doThrow(new RuntimeException("storage unavailable"))
                .when(bigQueryRepository).save(failedEvent);

        pubSub.publish(failedEvent);
        pubSub.publish(nextEvent);

        verify(processedEventRepository, timeout(1_000)).release(failedEvent.eventId());
        verify(metricsAggregator, never()).aggregate(failedEvent);
        verify(bigQueryRepository, timeout(1_000)).save(nextEvent);
        verify(metricsAggregator, timeout(1_000)).aggregate(nextEvent);
    }

    private static AdEvent event(String eventId) {
        return new AdEvent(eventId, "ad-1", "campaign-1", EventType.CLICK, Instant.EPOCH);
    }
}
