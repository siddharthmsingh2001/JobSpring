package com.jobspring.jobspring.exception;

public class SeekerSaveAlreadyExistsException extends Exception{

    public SeekerSaveAlreadyExistsException() {
        super("Seeker Save with the given attribute already exists");
    }

    public SeekerSaveAlreadyExistsException(String message) {
        super(message);
    }

    public SeekerSaveAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeekerSaveAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
