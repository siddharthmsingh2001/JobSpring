package com.jobspring.jobspring.exception;

public class InvalidTokenException extends Exception {

    public InvalidTokenException() {
        super("Entered OTP is invalid please try again");
    }

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(Throwable cause) {
        super(cause);
    }
}
