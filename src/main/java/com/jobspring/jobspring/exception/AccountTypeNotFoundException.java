package com.jobspring.jobspring.exception;

public class AccountTypeNotFoundException extends Exception{

    public AccountTypeNotFoundException() {
        super("AccountType with the given attribute not Found");
    }

    public AccountTypeNotFoundException(String message) {
        super(message);
    }

    public AccountTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountTypeNotFoundException(Throwable cause) {
        super(cause);
    }
}
