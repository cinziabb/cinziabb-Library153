package com.generation153.library.exception;

public class MaxLoansReachedException extends RuntimeException {
    public MaxLoansReachedException(String message) {
        super(message);
    }
}
