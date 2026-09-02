package com.plusOne.clicker.services;

import com.plusOne.clicker.bigtable.BigTableStore;
import com.plusOne.clicker.bigtable.MetricsSnapshot;
import com.plusOne.clicker.metrics.MetricKey;
import com.plusOne.clicker.response.MetricsResponse;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final BigTableStore bigTableStore;

    public MetricsService(BigTableStore bigTableStore) {
        this.bigTableStore = bigTableStore;
    }

    public MetricsResponse getMetrics(String campaignId, String adId) {

        MetricKey key = new MetricKey(campaignId, adId);

        MetricsSnapshot snapshot = bigTableStore.get(key);

        return new MetricsResponse(
                snapshot.impressions(),
                snapshot.clicks(),
                snapshot.registers()
        );
    }
}
