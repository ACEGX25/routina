package com.jin.routina.common.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Access denied: You do not own this resource");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
