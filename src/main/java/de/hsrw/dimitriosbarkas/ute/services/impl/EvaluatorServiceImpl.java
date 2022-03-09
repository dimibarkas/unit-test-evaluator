package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.SubmissionTO;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.persistence.user.UserService;
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

    private final UserService userService;

    public EvaluatorServiceImpl(ConfigService configService, SafeExecuteTestService safeExecuteTestService, UserService userService) {
        this.configService = configService;
        this.safeExecuteTestService = safeExecuteTestService;
        this.userService = userService;
    }

    @Override
    public SubmissionResult evaluateTest(SubmissionTO submissionTO) throws CannotLoadConfigException, TaskNotFoundException, CompilationErrorException {
        log.info("Evaluating test for task " + submissionTO.getTaskId() + " ...");

        // Get configuration for this task
        Task task = getTaskConfig(submissionTO.getTaskId());

        SubmissionResult result;
        try {
            safeExecuteTestService.setupTestEnvironment(task, submissionTO.getEncodedTestContent());
            result = safeExecuteTestService.buildAndRunTests();

//            userService.addSubmission();

            return result;
        } catch (CouldNotSetupTestEnvironmentException | ErrorWhileExecutingTestException e) {
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
