package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Exception that is thrown if tests could not be compiled.
 */
public class CompilationErrorException extends Exception {
    /**
     * Default constructor.
     */
    public CompilationErrorException() {
    }

    public CompilationErrorException(Throwable cause) {
        super(cause);
    }

}
