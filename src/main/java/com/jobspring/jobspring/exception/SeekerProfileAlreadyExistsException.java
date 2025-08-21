package com.jobspring.jobspring.exception;

public class SeekerProfileAlreadyExistsException extends Exception{

    public SeekerProfileAlreadyExistsException() {
        super("Seeker Profile with given attribute already exist");
    }

    public SeekerProfileAlreadyExistsException(String message) {
        super(message);
    }

    public SeekerProfileAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeekerProfileAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
