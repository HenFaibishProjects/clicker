package com.plusOne.clicker.bigtable;

import com.plusOne.clicker.domain.EventType;
import com.plusOne.clicker.metrics.MetricKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigTableInMemoryRepositoryTest {

    private final BigTableInMemoryRepository repository = new BigTableInMemoryRepository();
    private final MetricKey key = new MetricKey("campaign-1", "ad-1");

    @Test
    void incrementsClick() {
        repository.increment(key, EventType.CLICK);

        assertEquals(new MetricsSnapshot(0, 1, 0), repository.get(key));
    }

    @Test
    void incrementsImpression() {
        repository.increment(key, EventType.IMPRESSION);

        assertEquals(new MetricsSnapshot(1, 0, 0), repository.get(key));
    }

    @Test
    void incrementsRegister() {
        repository.increment(key, EventType.REGISTER);

        assertEquals(new MetricsSnapshot(0, 0, 1), repository.get(key));
    }

    @Test
    void keepsCountersIndependent() {
        repository.increment(key, EventType.IMPRESSION);
        repository.increment(key, EventType.IMPRESSION);
        repository.increment(key, EventType.CLICK);
        repository.increment(key, EventType.REGISTER);

        assertEquals(new MetricsSnapshot(2, 1, 1), repository.get(key));
    }

    @Test
    void isolatesDifferentMetricKeys() {
        MetricKey otherKey = new MetricKey("campaign-2", "ad-2");
        repository.increment(key, EventType.CLICK);
        repository.increment(otherKey, EventType.REGISTER);

        assertEquals(new MetricsSnapshot(0, 1, 0), repository.get(key));
        assertEquals(new MetricsSnapshot(0, 0, 1), repository.get(otherKey));
    }

    @Test
    void returnsZeroCountersForUnknownKey() {
        assertEquals(new MetricsSnapshot(0, 0, 0), repository.get(key));
    }
}
