package com.plusOne.clicker.services;

import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.domain.EventType;
import com.plusOne.clicker.mappers.AdEventMapper;
import com.plusOne.clicker.repositories.EventPublisher;
import com.plusOne.clicker.requests.AdEventRequest;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdEventServiceTest {

    @Test
    void mapsRequestAndPublishesMappedEvent() {
        EventPublisher publisher = mock(EventPublisher.class);
        AdEventMapper mapper = mock(AdEventMapper.class);
        AdEventRequest request = new AdEventRequest();
        request.setEventId("event-1");
        request.setAdId("ad-1");
        request.setCampaignId("campaign-1");
        request.setType(EventType.REGISTER);
        request.setTimestamp(Instant.EPOCH);
        AdEvent mappedEvent = new AdEvent("event-1", "ad-1", "campaign-1", EventType.REGISTER, Instant.EPOCH);
        when(mapper.toDomain(request)).thenReturn(mappedEvent);
        AdEventService service = new AdEventService(publisher, mapper);

        service.receiveEvent(request);

        verify(mapper).toDomain(request);
        verify(publisher).publish(mappedEvent);
    }
}
