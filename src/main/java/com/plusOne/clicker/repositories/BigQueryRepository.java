package com.plusOne.clicker.repositories;

import com.plusOne.clicker.domain.AdEvent;

public interface BigQueryRepository {

    void save(AdEvent event);
}