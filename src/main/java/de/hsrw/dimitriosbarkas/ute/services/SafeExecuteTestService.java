package de.hsrw.dimitriosbarkas.ute.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CouldNotSetupTestEnvironmentException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.ErrorWhileExecutingTestException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.JacocoReportXmlFileNotFoundException;

import java.io.IOException;
import java.nio.file.Path;

public interface SafeExecuteTestService {

    Path setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException;

    void safelyExecuteTestInTempProject(Path path) throws ErrorWhileExecutingTestException, IOException;

    void generateCoverageReport(Path path) throws IOException, InterruptedException;

    Report parseCoverageReport(Path path) throws IOException, JacocoReportXmlFileNotFoundException;

}
