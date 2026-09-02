
package com.plusOne.clicker.exceptions;

public class UnsupportedEventTypeException extends RuntimeException {

    public UnsupportedEventTypeException(String message) {
        super(message);
    }

    public UnsupportedEventTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}

