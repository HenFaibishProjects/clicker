package com.plusOne.clicker.publishers;

import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.messaging.InMemoryPubSub;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class InMemoryEventPublisher implements EventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(InMemoryEventPublisher.class);

    private final InMemoryPubSub pubSub;

    public InMemoryEventPublisher(InMemoryPubSub pubSub) {
        this.pubSub = pubSub;
    }

    @Override
    public void publish(AdEvent event) {
        log.info(
                "[component=InMemoryEventPublisher][action=publish] eventId={} type={}",
                event.eventId(),
                event.type()
        );
        pubSub.publish(event);
    }
}
