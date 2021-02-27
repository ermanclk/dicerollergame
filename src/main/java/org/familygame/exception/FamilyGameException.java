package org.familygame.exception;

public class FamilyGameException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public FamilyGameException(String message) {
        super(message);
    }

    public FamilyGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
