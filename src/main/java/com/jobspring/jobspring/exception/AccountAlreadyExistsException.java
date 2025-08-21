package com.jobspring.jobspring.exception;

public class AccountAlreadyExistsException extends Exception{

    public AccountAlreadyExistsException() {
        super("Account with given attribute already exists");
    }

    public AccountAlreadyExistsException(String message) {
        super(message);
    }

    public AccountAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
