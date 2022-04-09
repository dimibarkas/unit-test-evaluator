package de.hsrw.dimitriosbarkas.ute.services.exceptions;

import lombok.Data;

public class SourcefileNotFoundException extends Exception{

    private final String message;

    public SourcefileNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
