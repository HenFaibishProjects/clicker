package com.plusOne.clicker.requests;

import com.plusOne.clicker.domain.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class AdEventRequest
{
    @NotBlank(message = "eventId is required")
    String eventId;

    @NotBlank(message = "adId is required")
    String adId;

    @NotBlank(message = "campaignId is required")
    String campaignId;

    @NotNull(message = "type is required")
    EventType type;

    @NotNull(message = "timestamp is required")
    Instant timestamp;

}
