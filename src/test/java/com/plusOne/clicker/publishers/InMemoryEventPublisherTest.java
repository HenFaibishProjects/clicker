package com.plusOne.clicker.publishers;

import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.domain.EventType;
import com.plusOne.clicker.messaging.InMemoryPubSub;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InMemoryEventPublisherTest {

    @Test
    void delegatesEventToPubSub() {
        InMemoryPubSub pubSub = mock(InMemoryPubSub.class);
        InMemoryEventPublisher publisher = new InMemoryEventPublisher(pubSub);
        AdEvent event = new AdEvent("event-1", "ad-1", "campaign-1", EventType.IMPRESSION, Instant.EPOCH);

        publisher.publish(event);

        verify(pubSub).publish(event);
    }
}
