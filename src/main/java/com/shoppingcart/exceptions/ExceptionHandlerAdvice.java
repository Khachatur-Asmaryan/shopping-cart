package com.shoppingcart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler advice for handling specific exceptions.
 * <p>
 * This class is annotated with @ControllerAdvice, which allows it to handle exceptions
 * globally for all controllers in the application.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * Exception handler for NotFoundException.
     * <p>
     * This method handles NotFoundException and returns a ResponseEntity with a custom error message
     * and the corresponding HTTP status code (NOT_FOUND).
     *
     * @param e the NotFoundException that occurred
     * @return a ResponseEntity with the error message and HTTP status code
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException e) {
        return new ResponseEntity<>(createMessage(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for BadRequestException.
     * <p>
     * This method handles BadRequestException and returns a ResponseEntity with a custom error message
     * and the corresponding HTTP status code (BAD_REQUEST).
     *
     * @param e the BadRequestException that occurred
     * @return a ResponseEntity with the error message and HTTP status code
     */
    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<?> badRequestException(BadRequestException e) {
        return new ResponseEntity<>(createMessage(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for DuplicateDataException.
     * <p>
     * This method handles DuplicateDataException and returns a ResponseEntity with a custom error message
     * and the corresponding HTTP status code (CONFLICT).
     *
     * @param e the DuplicateDataException that occurred
     * @return a ResponseEntity with the error message and HTTP status code
     */
    @ExceptionHandler(DuplicateDataException.class)
    private ResponseEntity<?> duplicateDataException(DuplicateDataException e) {
        return new ResponseEntity<>(createMessage(e, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for AccessDeniedException.
     * <p>
     * This method handles AccessDeniedException (from Spring Security) and returns a ResponseEntity with
     * a custom error message and the corresponding HTTP status code (FORBIDDEN).
     *
     * @param ex the AccessDeniedException that occurred
     * @return a ResponseEntity with the error message and HTTP status code
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    protected ResponseEntity<Object> handleUnAuthenticated(
            org.springframework.security.access.AccessDeniedException ex) {
        return new ResponseEntity<>(createMessage(ex, HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    /**
     * Creates a custom error message for the given exception and HTTP status.
     * <p>
     * This method creates a Map object that contains the status code, timestamp, and error message.
     *
     * @param e      the exception for which to create the message
     * @param status the HTTP status code
     * @return a Map object with the error message details
     */
    private Map<String, Object> createMessage(Throwable e, HttpStatus status) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();

        errorResponse.put("statusCode", status.value());
        errorResponse.put("timestamp", new Date());
        errorResponse.put("error", e.getMessage());

        return errorResponse;
    }
}
