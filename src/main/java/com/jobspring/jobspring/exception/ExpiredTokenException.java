package com.jobspring.jobspring.exception;

public class ExpiredTokenException extends Exception{

    public ExpiredTokenException() {
        super("Entered Token has expired");
    }

    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredTokenException(Throwable cause) {
        super(cause);
    }
}
