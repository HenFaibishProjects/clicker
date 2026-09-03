package com.plusOne.clicker.repositories;

import com.plusOne.clicker.bigtable.MetricsSnapshot;
import com.plusOne.clicker.domain.EventType;
import com.plusOne.clicker.metrics.MetricKey;


public interface BigTableRepository {

    void increment(MetricKey key, EventType type);

    MetricsSnapshot get(MetricKey key);
}