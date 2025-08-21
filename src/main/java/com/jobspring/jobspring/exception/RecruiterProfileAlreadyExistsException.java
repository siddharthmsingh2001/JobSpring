package com.jobspring.jobspring.exception;

public class RecruiterProfileAlreadyExistsException extends Exception{

    public RecruiterProfileAlreadyExistsException() {
        super("Recruiter Profile with given attribute already exist");
    }

    public RecruiterProfileAlreadyExistsException(String message) {
        super(message);
    }

    public RecruiterProfileAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecruiterProfileAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
