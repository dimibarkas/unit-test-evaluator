package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
@Log4j2
public class EvaluatorServiceImpl implements EvaluatorService {

    private final ConfigService configService;

    private final SafeExecuteTestService safeExecuteTestService;

    public EvaluatorServiceImpl(
            ConfigService configService,
            SafeExecuteTestService safeExecuteTestService
    ) {
        this.configService = configService;
        this.safeExecuteTestService = safeExecuteTestService;
    }

    @Override
    public TestResult evaluateTest(String taskId, String encodedTestContent) throws CannotLoadConfigException, TaskNotFoundException, CompilationErrorException {
        log.info("Evaluating test for task " + taskId + " ...");

        // Get configuration for this task
        Task task = getTaskConfig(taskId);

        Path path;
        Report report;
        TestResult result;
        try {
            path = safeExecuteTestService.setupTestEnvironment(task, encodedTestContent);
            result = safeExecuteTestService.executeTestInTempDirectory(path);

            //check if build was successful by exit value
            if(result.getProcessExitValue() == 0) {
                log.info("build successful");
                result.setSummary(BuildSummary.BUILD_SUCCESSFUL);
                safeExecuteTestService.generateCoverageReport(path);
                report = safeExecuteTestService.parseCoverageReport(path);
                result.setReport(report);
                //log.info(result);
            } else {
                if(!checkPath(path)) {
                    log.info("build failed");
                    result.setSummary(BuildSummary.BUILD_FAILED);
                    //throw new ErrorWhileBuildingTestException();
                } else {
                    log.info("build successful - but there are test failures");
                    result.setSummary(BuildSummary.TESTS_FAILED);
                }
            }
            return result;
        } catch (CouldNotSetupTestEnvironmentException |  ErrorWhileExecutingTestException | ErrorWhileGeneratingCoverageReport  | JacocoReportXmlFileNotFoundException | IOException  e) {
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

    /**
     * This function helps to get a Task by its id.
     * @param taskId the specified id
     * @return Task
     * @throws CannotLoadConfigException if config could not be load
     * @throws TaskNotFoundException if the task with the specified can not be found
     */
    private Task getTaskConfig(String taskId) throws CannotLoadConfigException, TaskNotFoundException {
        TaskConfig config = configService.getTaskConfig();

        //Find task
        return config.getTasks().stream().filter(t -> taskId.equals(t.getId()))
                .findFirst().orElseThrow(TaskNotFoundException::new);
    }
}
