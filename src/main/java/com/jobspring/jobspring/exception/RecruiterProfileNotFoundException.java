package com.jobspring.jobspring.exception;

public class RecruiterProfileNotFoundException extends Exception{

    public RecruiterProfileNotFoundException() {
        super("Recruiter Profile with given attributes not Found");
    }

    public RecruiterProfileNotFoundException(String message) {
        super(message);
    }

    public RecruiterProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecruiterProfileNotFoundException(Throwable cause) {
        super(cause);
    }
}
