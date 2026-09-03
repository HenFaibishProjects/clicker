package com.plusOne.clicker.bigtable;

import com.plusOne.clicker.domain.EventType;
import com.plusOne.clicker.metrics.MetricKey;


public interface BigTableRepository {

    void increment(MetricKey key, EventType type);

    MetricsSnapshot get(MetricKey key);
}