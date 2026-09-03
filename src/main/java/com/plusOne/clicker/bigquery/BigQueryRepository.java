package com.plusOne.clicker.bigquery;

import com.plusOne.clicker.domain.AdEvent;

import java.util.List;

public interface BigQueryRepository {

    void save(AdEvent event);

    List<AdEvent> findAll();
}