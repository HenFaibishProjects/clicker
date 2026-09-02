package com.plusOne.clicker.redis;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisInMemory implements RedisStore {

    private final Set<String> processedEventIds =
            ConcurrentHashMap.newKeySet();

    @Override
    public boolean markIfNew(String eventId) {
        return processedEventIds.add(eventId);
    }
}
