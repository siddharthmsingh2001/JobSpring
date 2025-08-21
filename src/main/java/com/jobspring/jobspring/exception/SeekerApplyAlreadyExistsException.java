package com.jobspring.jobspring.exception;

public class SeekerApplyAlreadyExistsException extends Exception{

    public SeekerApplyAlreadyExistsException() {
        super("Seeker Apply with given attribute already exists");
    }

    public SeekerApplyAlreadyExistsException(String message) {
        super(message);
    }

    public SeekerApplyAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeekerApplyAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
