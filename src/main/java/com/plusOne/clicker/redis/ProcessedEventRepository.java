package com.plusOne.clicker.redis;

public interface ProcessedEventRepository {

    boolean tryClaim(String eventId);

    void release(String eventId);
}
