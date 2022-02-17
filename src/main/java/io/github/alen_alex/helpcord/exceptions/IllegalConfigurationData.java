package io.github.alen_alex.helpcord.exceptions;

public class IllegalConfigurationData extends RuntimeException{

    public IllegalConfigurationData(String message) {
        super(message);
    }

    public IllegalConfigurationData(String message, Throwable cause) {
        super(message, cause);
    }
}
