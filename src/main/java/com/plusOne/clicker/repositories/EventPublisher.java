package com.plusOne.clicker.repositories;

import com.plusOne.clicker.domain.AdEvent;

public interface EventPublisher {

    void publish(AdEvent event);
}
