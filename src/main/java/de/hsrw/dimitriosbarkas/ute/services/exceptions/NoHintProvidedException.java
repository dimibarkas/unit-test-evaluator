package de.hsrw.dimitriosbarkas.ute.services.exceptions;

public class NoHintProvidedException extends Exception{

    private final int lineNumber;

    public NoHintProvidedException(int lineNumber) {
        super();
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }
}
