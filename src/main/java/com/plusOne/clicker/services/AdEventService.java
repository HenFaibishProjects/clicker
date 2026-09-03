package com.plusOne.clicker.services;

import com.plusOne.clicker.domain.AdEvent;
import com.plusOne.clicker.mappers.AdEventMapper;
import com.plusOne.clicker.repositories.EventPublisher;
import com.plusOne.clicker.requests.AdEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdEventService {
    private final EventPublisher eventPublisher;
    private final AdEventMapper adEventMapper;

    private static final Logger log =
            LoggerFactory.getLogger(AdEventService.class);

    public AdEventService(EventPublisher eventPublisher, AdEventMapper adEventMapper) {
        this.eventPublisher = eventPublisher;
        this.adEventMapper = adEventMapper;
    }

    public void receiveEvent(AdEventRequest request) {

        log.info(
                "[component=AdEventService][action=receiveEvent] eventId={} type={}",
                request.getEventId(),
                request.getType()
        );
        AdEvent event = adEventMapper.toDomain(request);
        eventPublisher.publish(event);
    }
}
