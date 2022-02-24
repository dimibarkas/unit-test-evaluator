package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TestResult;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CouldNotSetupTestEnvironmentException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.ErrorWhileExecutingTestException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.JacocoReportXmlFileNotFoundException;

import java.io.IOException;
import java.nio.file.Path;

public interface SafeExecuteTestService {
    /**
     * This function creates a maven project in a temporary directory where unit tests can be executed in.
     * @param task the specified task which needs to be setup
     * @param encodedTest the base 64 encoded test
     * @return the path to the temporary directory
     * @throws CouldNotSetupTestEnvironmentException if an error occurs while setting up the test environment
     */
    Path setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException;

    /**
     * This function actually calls the command to compile the provided tests.
     * @param path the path to the temporary directory
     * @return the exit value of the process
     * @throws ErrorWhileExecutingTestException if an error occurs while executing tests (compile oder test errors)
     */
    TestResult executeTestInTempDirectory(Path path) throws ErrorWhileExecutingTestException, IOException, InterruptedException;
    
    //return the exit value of this process
    void generateCoverageReport(Path path) throws IOException, InterruptedException;

    Report parseCoverageReport(Path path) throws IOException, JacocoReportXmlFileNotFoundException;

}
