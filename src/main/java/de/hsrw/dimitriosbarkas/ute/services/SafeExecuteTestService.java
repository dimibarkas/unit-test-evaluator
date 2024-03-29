package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.model.pitest.MutationReport;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;

import java.nio.file.Path;

/**
 * This interface provides methods for the base functionality of this project like
 * setting up a test environment or executing tests in this environment.
 */
public interface SafeExecuteTestService {
    /**
     * This function creates a maven project in a temporary directory where unit tests can be executed in.
     * @param task the specified task which needs to be setup
     * @param encodedTest the base 64 encoded test
     * @throws CouldNotSetupTestEnvironmentException if an error occurs while setting up the test environment
     */
    Path setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException;

    /**
     * This function actually calls the command to compile the provided tests.
     * @return the exit value of the process
     * @throws ErrorWhileExecutingTestException if an error occurs while executing tests (compile oder test errors)
     */
    SubmissionResult buildAndRunTests(Task task, Path path) throws ErrorWhileExecutingTestException;

    /**
     * This function calls the command to generate a coverage-report.xml file out of the jacoco.exe file.
     * @param path the path to the temporary maven directory
     * @throws ErrorWhileGeneratingCoverageReport if an error occurs in time of the generation ( e.g. resource file could not be found)
     */
    void generateCoverageReport(Path path) throws ErrorWhileGeneratingCoverageReport;

    /**
     * This function calls the command to generate a mutation-report as xml.
     * @param path the path to the temporary maven directory.
     */
    void generateMutationCoverageReport(Path path);

    /**
     * This function tries to parse a report created by jacoco into a Report object.
     * @param path the path to the jacoco directory.
     * @return the parsed Report object
     * @throws JacocoReportXmlFileNotFoundException if the xml file could not be found
     */
    Report parseCoverageReport(Path path) throws JacocoReportXmlFileNotFoundException, ErrorWhileParsingReportException;

    /**
     * This function tries to parse a report created by pitest.
     * @param path the path to the pitest directory.
     * @return the parsed mutation report
     * @throws ErrorWhileParsingReportException if the xml file could not be found.
     */
    MutationReport parseMutationCoverageReport(Path path) throws ErrorWhileParsingReportException;
}
