package com.shoppingcart.exceptions;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
