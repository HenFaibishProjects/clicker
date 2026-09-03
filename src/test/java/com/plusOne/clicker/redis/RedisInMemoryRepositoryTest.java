package com.plusOne.clicker.redis;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisInMemoryRepositoryTest {

    @Test
    void firstClaimSucceedsAndDuplicateClaimFails() {
        RedisInMemoryRepository repository = new RedisInMemoryRepository(Duration.ofMinutes(1));

        assertTrue(repository.tryClaim("event-1"));
        assertFalse(repository.tryClaim("event-1"));
    }

    @Test
    void releaseAllowsEventToBeClaimedAgain() {
        RedisInMemoryRepository repository = new RedisInMemoryRepository(Duration.ofMinutes(1));
        repository.tryClaim("event-1");

        repository.release("event-1");

        assertTrue(repository.tryClaim("event-1"));
    }

    @Test
    void expiredEventCanBeClaimedAgain() {
        RedisInMemoryRepository repository = new RedisInMemoryRepository(Duration.ofNanos(-1));

        assertTrue(repository.tryClaim("event-1"));
        assertTrue(repository.tryClaim("event-1"));
    }
}
