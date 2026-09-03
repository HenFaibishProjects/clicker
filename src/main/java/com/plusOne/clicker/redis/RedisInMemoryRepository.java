package com.plusOne.clicker.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Repository
public class RedisInMemoryRepository implements ProcessedEventRepository {

    private final ConcurrentHashMap<String, Instant> processedEvents =
            new ConcurrentHashMap<>();

    private final Duration ttl;

    public RedisInMemoryRepository(
            @Value("${deduplication.ttl}") Duration ttl) {
        this.ttl = ttl;
    }

    @Override
    public boolean tryClaim(String eventId) {

        Instant now = Instant.now();
        Instant newExpiration = now.plus(ttl);

        AtomicBoolean claimed = new AtomicBoolean(false);

        processedEvents.compute(eventId, (key, currentExpiration) -> {

            if (currentExpiration == null ||
                    currentExpiration.isBefore(now)) {

                claimed.set(true);
                return newExpiration;
            }

            return currentExpiration;
        });

        return claimed.get();
    }

    @Override
    public void release(String eventId) {
        processedEvents.remove(eventId);
    }
}
