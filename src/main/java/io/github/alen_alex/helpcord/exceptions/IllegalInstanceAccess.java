package io.github.alen_alex.helpcord.exceptions;

public class IllegalInstanceAccess extends RuntimeException{

    public IllegalInstanceAccess(String message) {
        super(message);
    }

    public IllegalInstanceAccess(String message, Throwable cause) {
        super(message, cause);
    }
}
