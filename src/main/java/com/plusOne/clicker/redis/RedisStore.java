package com.plusOne.clicker.redis;

public interface RedisStore {

    boolean markIfNew(String eventId);
}
