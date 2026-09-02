package com.plusOne.clicker.bigtable;

import com.plusOne.clicker.domain.EventType;
import com.plusOne.clicker.metrics.MetricKey;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Component
public class BigTableInMemory implements BigTableStore {

    private final ConcurrentHashMap<MetricKey, Counters> counters =
            new ConcurrentHashMap<>();

    @Override
    public void increment(MetricKey key, EventType type) {

        counters
                .computeIfAbsent(key, ignored -> new Counters())
                .increment(type);
    }

    @Override
    public MetricsSnapshot get(MetricKey key) {

        Counters counters = this.counters.get(key);

        if (counters == null) {
            return new MetricsSnapshot(0, 0, 0);
        }

        return counters.snapshot();
    }

    private static class Counters {

        private final LongAdder impressions = new LongAdder();
        private final LongAdder clicks = new LongAdder();
        private final LongAdder registers = new LongAdder();

        void increment(EventType type) {
            switch (type) {
                case IMPRESSION -> impressions.increment();
                case CLICK -> clicks.increment();
                case REGISTER -> registers.increment();
            }
        }

        MetricsSnapshot snapshot() {
            return new MetricsSnapshot(
                    impressions.sum(),
                    clicks.sum(),
                    registers.sum()
            );
        }
    }
}