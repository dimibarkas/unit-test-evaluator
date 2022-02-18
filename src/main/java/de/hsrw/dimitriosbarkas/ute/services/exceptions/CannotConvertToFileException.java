package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Expection that is thrown if test content cannot be converted to a file.
 */
public class CannotConvertToFileException extends Exception {
    /**
     * Default constructor.
     */
    public CannotConvertToFileException() {

    }

    public CannotConvertToFileException(Throwable cause) {
        super(cause);
    }
}
