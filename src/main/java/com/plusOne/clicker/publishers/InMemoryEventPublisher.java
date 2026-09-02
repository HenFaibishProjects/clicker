package com.plusOne.clicker.publishers;

import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.messaging.InMemoryPubSub;
import org.springframework.stereotype.Component;

@Component
public class InMemoryEventPublisher implements EventPublisher {

    private final InMemoryPubSub pubSub;

    public InMemoryEventPublisher(InMemoryPubSub pubSub) {
        this.pubSub = pubSub;
    }

    @Override
    public void publish(AdEvent event) {
        pubSub.publish(event);
    }
}
