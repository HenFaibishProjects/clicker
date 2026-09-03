package com.plusOne.clicker.metrics;

import com.plusOne.clicker.repositories.BigTableRepository;
import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.domain.EventType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MetricsAggregatorTest {

    @Test
    void buildsMetricKeyAndIncrementsMatchingEventType() {
        BigTableRepository repository = mock(BigTableRepository.class);
        MetricsAggregator aggregator = new MetricsAggregator(repository);
        AdEvent event = new AdEvent("event-1", "ad-1", "campaign-1", EventType.CLICK, Instant.EPOCH);

        aggregator.aggregate(event);

        verify(repository).increment(new MetricKey("campaign-1", "ad-1"), EventType.CLICK);
    }
}
