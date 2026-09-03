package com.plusOne.clicker.services;

import com.plusOne.clicker.repositories.BigTableRepository;
import com.plusOne.clicker.bigtable.MetricsSnapshot;
import com.plusOne.clicker.metrics.MetricKey;
import com.plusOne.clicker.response.MetricsResponse;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final BigTableRepository bigTableRepository;

    public MetricsService(BigTableRepository bigTableRepository) {
        this.bigTableRepository = bigTableRepository;
    }

    public MetricsResponse getMetrics(String campaignId, String adId) {

        MetricKey key = new MetricKey(campaignId, adId);

        MetricsSnapshot snapshot = bigTableRepository.get(key);

        return new MetricsResponse(
                snapshot.impressions(),
                snapshot.clicks(),
                snapshot.registers()
        );
    }
}
