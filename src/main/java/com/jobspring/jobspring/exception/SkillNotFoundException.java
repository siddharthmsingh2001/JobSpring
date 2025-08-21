package com.jobspring.jobspring.exception;

public class SkillNotFoundException extends Exception{

    public SkillNotFoundException() {
        super("Skill with the given attribute not found");
    }

    public SkillNotFoundException(String message) {
        super(message);
    }

    public SkillNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkillNotFoundException(Throwable cause) {
        super(cause);
    }
}
