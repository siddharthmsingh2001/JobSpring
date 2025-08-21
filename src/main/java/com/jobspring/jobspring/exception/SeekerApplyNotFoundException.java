package com.jobspring.jobspring.exception;

public class SeekerApplyNotFoundException extends Exception{

    public SeekerApplyNotFoundException() {
        super("Seeker Apply with given attribute not found");
    }

    public SeekerApplyNotFoundException(String message) {
        super(message);
    }

    public SeekerApplyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeekerApplyNotFoundException(Throwable cause) {
        super(cause);
    }
}
