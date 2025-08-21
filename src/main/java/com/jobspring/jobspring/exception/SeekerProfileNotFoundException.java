package com.jobspring.jobspring.exception;

public class SeekerProfileNotFoundException extends Exception{

    public SeekerProfileNotFoundException() {
        super("Seeker Profile with given attribute not found");
    }

    public SeekerProfileNotFoundException(String message) {
        super(message);
    }

    public SeekerProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeekerProfileNotFoundException(Throwable cause) {
        super(cause);
    }
}
