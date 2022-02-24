package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.model.TestResult;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
@Log4j2
public class EvaluatorServiceImpl implements EvaluatorService {

    @Autowired
    private ConfigService configService;

    @Autowired
    private SafeExecuteTestService safeExecuteTestService;

    @Override
    public String evaluateTest(String taskId, String encodedTestContent) throws CannotLoadConfigException, TaskNotFoundException, CompilationErrorException {
        log.info("Evaluating test for task " + taskId + " ...");

        // Get configuration for this task
        Task task = getTaskConfig(taskId);

        Path path;
        Report report;
        try {
            path = safeExecuteTestService.setupTestEnvironment(task, encodedTestContent);
            TestResult result = safeExecuteTestService.executeTestInTempDirectory(path);

            //check if there are errors
            if(result.getProcessExitValue() == 0) {
                safeExecuteTestService.generateCoverageReport(path);
                report = safeExecuteTestService.parseCoverageReport(path);
                result.setReport(report);
                //log.info(result);
            } else {
                if(!checkPath(path)) {
                    log.info("build failed");
                    //log.info(result.getProcessOutput());
                    //throw new ErrorWhileBuildingTestException();
                } else {
                    log.info("there are test failures");
                }
            }
            return result.getProcessOutput();
        } catch (CouldNotSetupTestEnvironmentException |  ErrorWhileExecutingTestException | IOException | InterruptedException  | JacocoReportXmlFileNotFoundException e) {
            log.error(e);
            throw new CompilationErrorException(e);
        }
    }


    /**
     * This method checks if a directory exists in a given path.
     * @param path specified path
     * @return boolean value if directory exists
     */
    private boolean checkPath(Path path) {
        File dir = new File(path.toAbsolutePath() + "/testapp/target/surefire-reports");
        return dir.exists();
    }

    private Task getTaskConfig(String taskId) throws CannotLoadConfigException, TaskNotFoundException {
        TaskConfig config = configService.getTaskConfig();

        //Find task
        return config.getTasks().stream().filter(t -> taskId.equals(t.getId()))
                .findFirst().orElseThrow(TaskNotFoundException::new);
    }
}
