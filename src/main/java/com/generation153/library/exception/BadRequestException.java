package com.generation153.library.exception;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
