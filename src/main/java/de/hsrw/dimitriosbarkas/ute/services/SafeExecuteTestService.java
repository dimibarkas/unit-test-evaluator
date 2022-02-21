package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CouldNotSetupTestEnvironmentException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.ErrorWhileExecutingTestException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.JacocoReportXmlFileNotFoundException;

import java.io.IOException;
import java.nio.file.Path;

public interface SafeExecuteTestService {

    Path setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException;

    //return the exit value of this process
    void safelyExecuteTestInTempProject(Path path) throws ErrorWhileExecutingTestException, IOException;
    
    //return the exit value of this process
    void generateCoverageReport(Path path) throws IOException, InterruptedException;

    Report parseCoverageReport(Path path) throws IOException, JacocoReportXmlFileNotFoundException;

}
