package com.shoppingcart.exceptions;

public class DuplicateDataException extends Exception {

    public DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
