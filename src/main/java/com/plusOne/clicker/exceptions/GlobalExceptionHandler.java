package com.plusOne.clicker.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidAdEventException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAdEvent(
            InvalidAdEventException exception) {

        log.warn(
                "[component=GlobalExceptionHandler][action=handleInvalidAdEvent] message={}",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "INVALID_AD_EVENT",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(UnsupportedEventTypeException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedEventType(
            UnsupportedEventTypeException exception) {

        log.warn(
                "[component=GlobalExceptionHandler][action=handleUnsupportedEventType] message={}",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "UNSUPPORTED_EVENT_TYPE",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Invalid ad event");

        log.warn(
                "[component=GlobalExceptionHandler][action=handleValidationException] message={}",
                message
        );

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        "INVALID_AD_EVENT",
                        message
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception) {

        log.warn(
                "[component=GlobalExceptionHandler][action=handleHttpMessageNotReadable] message={}",
                exception.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        "INVALID_REQUEST",
                        "Invalid request body or unsupported field value"
                ));
    }
}
