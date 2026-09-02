
package com.plusOne.clicker.exceptions;

public class InvalidAdEventException extends RuntimeException {

    public InvalidAdEventException(String message) {
        super(message);
    }

    public InvalidAdEventException(String message, Throwable cause) {
        super(message, cause);
    }
}

