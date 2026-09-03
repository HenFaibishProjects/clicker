package com.plusOne.clicker.bigquery;

import com.plusOne.clicker.domain.AdEvent;

public interface BigQueryRepository {

    void save(AdEvent event);
}