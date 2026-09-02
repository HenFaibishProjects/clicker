package com.plusOne.clicker.exceptions;

public class AdEventException extends RuntimeException {

    public AdEventException(String message) {
        super(message);
    }

    public AdEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
