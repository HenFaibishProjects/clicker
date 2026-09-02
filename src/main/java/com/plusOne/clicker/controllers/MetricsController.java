package com.plusOne.clicker.controllers;

import com.plusOne.clicker.response.MetricsResponse;
import com.plusOne.clicker.services.MetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping
    public ResponseEntity<MetricsResponse> getMetrics(
            @RequestParam String campaignId,
            @RequestParam String adId) {

        return ResponseEntity.ok(
                metricsService.getMetrics(campaignId, adId)
        );
    }
}
