package com.plusOne.clicker.bigtable;

public record MetricsSnapshot(
        long impressions,
        long clicks,
        long registers
) {
}