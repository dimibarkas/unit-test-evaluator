package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Excpetion that is thrown if Task could not be found in config.
 */
public class TaskNotFoundException extends Exception {
    /**
     * Default constructor.
     */
    public TaskNotFoundException() {

    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }
}
