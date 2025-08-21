package com.jobspring.jobspring.exception;

public class SeekerSaveNotFoundException extends Exception{

    public SeekerSaveNotFoundException() {
        super("Seeker Save with the given attribute not found");
    }

    public SeekerSaveNotFoundException(String message) {
        super(message);
    }

    public SeekerSaveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeekerSaveNotFoundException(Throwable cause) {
        super(cause);
    }
}
