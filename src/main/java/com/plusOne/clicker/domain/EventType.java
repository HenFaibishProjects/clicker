package com.plusOne.clicker.domain;

import java.util.Arrays;

public enum EventType {

    IMPRESSION,
    CLICK,
    REGISTER;

    public static EventType from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Event type must not be empty");
        }

        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unsupported event type: " + value
                        )
                );
    }
}
