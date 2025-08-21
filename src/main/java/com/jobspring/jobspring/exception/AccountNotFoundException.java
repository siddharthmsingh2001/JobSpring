package com.jobspring.jobspring.exception;

public class AccountNotFoundException extends Exception{

    public AccountNotFoundException() {
        super("Account with the given attribute does not exist");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
