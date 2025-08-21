package com.jobspring.jobspring.exception;

public class UnauthorizedUserException extends Exception{

    public UnauthorizedUserException() {
        super("Unauthorized user");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }

    public UnauthorizedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedUserException(Throwable cause) {
        super(cause);
    }
}
