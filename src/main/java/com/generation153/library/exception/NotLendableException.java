package com.generation153.library.exception;

@SuppressWarnings("serial")
public class NotLendableException extends RuntimeException {
    public NotLendableException(String message) {
        super(message);
    }
}
