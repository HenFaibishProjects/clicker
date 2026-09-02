package com.plusOne.clicker.metrics;

import com.plusOne.clicker.bigtable.BigTableStore;
import com.plusOne.clicker.domain.AdEvent;
import org.springframework.stereotype.Service;

@Service
public class MetricsAggregator {

    private final BigTableStore bigTableStore;

    public MetricsAggregator(BigTableStore bigTableStore) {
        this.bigTableStore = bigTableStore;
    }

    public void aggregate(AdEvent event) {

        MetricKey key = new MetricKey(
                event.campaignId(),
                event.adId()
        );

        bigTableStore.increment(key, event.type());
    }
}