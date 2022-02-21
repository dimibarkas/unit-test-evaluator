package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Exception that is thrown if tests could not be compiled.
 */
public class CompliationErrorException extends Exception {
    /**
     * Default constructor.
     */
    public CompliationErrorException() {
    }

    public CompliationErrorException(Throwable cause) {
        super(cause);
    }

}
