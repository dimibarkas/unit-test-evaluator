package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Custom Exception that is thrown if an error occured while setting up the test environment.
 */
public class CouldNotSetupTestEnvironmentException extends Exception {
    /**
     * Default constructor.
     */
    public CouldNotSetupTestEnvironmentException() {

    }

    public CouldNotSetupTestEnvironmentException(Throwable cause) {
        super(cause);
    }



}
