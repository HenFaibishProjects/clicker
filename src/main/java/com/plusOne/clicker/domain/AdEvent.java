package com.plusOne.clicker.domain;

import java.time.Instant;

public record AdEvent(
        String eventId,
        String adId,
        String campaignId,
        @jakarta.validation.constraints.NotNull(message = "type is required") EventType type,
        Instant timestamp)
{}
