package com.jobspring.jobspring.exception;

public class JobLocationNotFoundException extends Exception{

    public JobLocationNotFoundException() {
        super("Job Location with the given attribute not found");
    }

    public JobLocationNotFoundException(String message) {
        super(message);
    }

    public JobLocationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobLocationNotFoundException(Throwable cause) {
        super(cause);
    }
}
