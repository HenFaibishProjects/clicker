package com.plusOne.clicker.exceptions;

public record ErrorResponse(
        String code,
        String message
) {
}
