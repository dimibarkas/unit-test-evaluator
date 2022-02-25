package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Exception that is thrown if jacoco report file cannot be found.
 */
public class JacocoReportXmlFileNotFoundException extends Exception {

    /**
     * Default constructor.
     */
    public JacocoReportXmlFileNotFoundException() {
    }

    /**
     * Constructor setting cause.
     * @param message description of why the exception was thrown.
     */
    public JacocoReportXmlFileNotFoundException(String message) {
        super(message);
    }


}
