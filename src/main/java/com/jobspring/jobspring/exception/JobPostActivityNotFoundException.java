package com.jobspring.jobspring.exception;

public class JobPostActivityNotFoundException extends Exception{

    public JobPostActivityNotFoundException() {
        super("Job Post Activity with given attribute not found");
    }

    public JobPostActivityNotFoundException(String message) {
        super(message);
    }

    public JobPostActivityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobPostActivityNotFoundException(Throwable cause) {
        super(cause);
    }
}
