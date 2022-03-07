package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.Submission;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.model.TestResult;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EvaluatorServiceImpl implements EvaluatorService {

    private final ConfigService configService;

    private final SafeExecuteTestService safeExecuteTestService;

    public EvaluatorServiceImpl(ConfigService configService, SafeExecuteTestService safeExecuteTestService) {
        this.configService = configService;
        this.safeExecuteTestService = safeExecuteTestService;
    }

    @Override
    public TestResult evaluateTest(Submission submission) throws CannotLoadConfigException, TaskNotFoundException, CompilationErrorException {
        log.info("Evaluating test for task " + submission.getTaskId() + " ...");

        // Get configuration for this task
        Task task = getTaskConfig(submission.getTaskId());

        TestResult result;
        try {
            safeExecuteTestService.setupTestEnvironment(task, submission.getEncodedTestContent());
            result = safeExecuteTestService.buildAndRunTests();

            return result;
        } catch (CouldNotSetupTestEnvironmentException | ErrorWhileExecutingTestException  e) {
            log.error(e);
            throw new CompilationErrorException(e);
        }
    }

    /**
     * This function helps to get a Types by its id.
     *
     * @param taskId the specified id
     * @return Types
     * @throws CannotLoadConfigException if config could not be load
     * @throws TaskNotFoundException     if the task with the specified can not be found
     */
    private Task getTaskConfig(String taskId) throws CannotLoadConfigException, TaskNotFoundException {
        TaskConfig config = configService.getTaskConfig();

        //Find task
        return config.getTasks().stream().filter(t -> taskId.equals(t.getId())).findFirst().orElseThrow(TaskNotFoundException::new);
    }
}
