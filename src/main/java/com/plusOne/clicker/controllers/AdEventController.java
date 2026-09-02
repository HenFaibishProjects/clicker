package com.plusOne.clicker.controllers;

import com.plusOne.clicker.requests.AdEventRequest;
import com.plusOne.clicker.services.AdEventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/events")
public class AdEventController {

    private final AdEventService adEventService;

    public AdEventController(AdEventService adEventService) {
        this.adEventService = adEventService;
    }

    @PostMapping
    public ResponseEntity<Void> receiveEvent(@Valid @RequestBody AdEventRequest request) {
        adEventService.receiveEvent(request);
        return ResponseEntity.accepted().build();
    }
}
