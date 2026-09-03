package com.plusOne.clicker.metrics;

import com.plusOne.clicker.bigtable.BigTableRepository;
import com.plusOne.clicker.domain.AdEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MetricsAggregator {

    private final BigTableRepository bigTableRepository;

    private static final Logger log =
            LoggerFactory.getLogger(MetricsAggregator.class);

    public MetricsAggregator(BigTableRepository bigTableRepository) {
        this.bigTableRepository = bigTableRepository;
    }

    public void aggregate(AdEvent event) {

        MetricKey key = new MetricKey(
                event.campaignId(),
                event.adId()
        );

        log.info(
                "[component=MetricsAggregator][action=aggregate] campaignId={} adId={} type={}",
                event.campaignId(),
                event.adId(),
                event.type()
        );

        bigTableRepository.increment(key, event.type());
    }
}