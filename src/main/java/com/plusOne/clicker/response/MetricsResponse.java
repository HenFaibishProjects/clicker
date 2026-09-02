package com.plusOne.clicker.response;

public record MetricsResponse(
        long impressions,
        long clicks,
        long registers
) {
}