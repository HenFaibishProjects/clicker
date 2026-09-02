package com.plusOne.clicker.publishers;

import com.plusOne.clicker.domain.AdEvent;

public interface EventPublisher {

    void publish(AdEvent event);
}
