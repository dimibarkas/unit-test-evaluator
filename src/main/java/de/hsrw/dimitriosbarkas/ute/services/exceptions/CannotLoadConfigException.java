package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Exception that is thrown if config cannot be loaded.
 */
public class CannotLoadConfigException extends Exception {
    /**
     * Default constructor.
     */
    public CannotLoadConfigException() {

    }

    /**
     * Constructor setting cause.
     * @param cause cause of exception
     */
    public CannotLoadConfigException(Throwable cause) {
        super(cause);
    }
}
