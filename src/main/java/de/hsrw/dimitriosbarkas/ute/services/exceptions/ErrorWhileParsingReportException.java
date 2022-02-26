package de.hsrw.dimitriosbarkas.ute.services.exceptions;

/**
 * Exception that is thrown if an error occurs while reading the jacoco.xml file.
 */
public class ErrorWhileParsingReportException extends Exception {

    public ErrorWhileParsingReportException() {
    }

    public ErrorWhileParsingReportException(Throwable cause) {
        super(cause);
    }
}
