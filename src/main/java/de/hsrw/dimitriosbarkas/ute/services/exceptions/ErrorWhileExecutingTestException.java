package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Custom exception that is thrown if an error occurred while executing tests.
 */
public class ErrorWhileExecutingTestException extends Exception{
    /**
     *  Default constructor.
     */
    public ErrorWhileExecutingTestException() {

    }

    public ErrorWhileExecutingTestException(Throwable cause) {
        super(cause);
    }

    public ErrorWhileExecutingTestException(String message) {
        super(message);
    }

}
