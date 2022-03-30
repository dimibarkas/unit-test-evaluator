package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.SubmissionTO;
import de.hsrw.dimitriosbarkas.ute.model.*;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Counter;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport._Class;
import de.hsrw.dimitriosbarkas.ute.persistence.user.User;
import de.hsrw.dimitriosbarkas.ute.persistence.user.UserService;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.FeedbackService;
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

    private final FeedbackService feedbackService;

    public EvaluatorServiceImpl(ConfigService configService, SafeExecuteTestService safeExecuteTestService, UserService userService, FeedbackService feedbackService) {
        this.configService = configService;
        this.safeExecuteTestService = safeExecuteTestService;
        this.userService = userService;
        this.feedbackService = feedbackService;
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

            if (result.getSummary() == BuildSummary.BUILD_SUCCESSFUL) {
                CoverageResult coverageResult = getCoverageResult(result.getReport(), task);
                userService.addSubmission(submissionTO.getUserId(), submissionTO.getTaskId(), coverageResult.getCoveredInstructions(), coverageResult.getCoveredBranches(), result.getSummary());
            } else {
                userService.addSubmission(submissionTO.getUserId(), submissionTO.getTaskId(), 0, 0, result.getSummary());
            }

            try {
                User user = userService.getUserById(submissionTO.getUserId());
                result.setFeedback(feedbackService.provideFeedback(user, task, result));
            } catch (NullPointerException e) {
                log.warn("no hint provided for specific line");
            }

            return result;
        } catch (CouldNotSetupTestEnvironmentException | ErrorWhileExecutingTestException e) {
            log.error(e);
            throw new CompilationErrorException(e);
        }
    }

    @Override
    public CoverageResult getCoverageResult(Report report, Task task) {
        //TODO: make function throw a custom exception
        _Class _class = null;
        try {
            _class = report.get_package().get_class().stream().filter(aClass -> aClass.getSourcefilename().equals(task.getSourcefilename())).findFirst().orElseThrow(Exception::new);
        } catch (Exception e) {
            log.error(e);
        }
        return new CoverageResult(calculateCoveredInstructionsPercent(_class), calculateCoveredBranchesPercent(_class));
    }

    public int calculateCoveredInstructionsPercent(_Class _class) {
        //TODO: make function throw a custom exception
        if (_class == null) return 0;
        int percentage = 0;
        try {
            Counter counter = _class.getCounter().stream().filter(c -> c.getType().equals("INSTRUCTION")).findFirst().orElseThrow(Exception::new);
            //log.info(counter.covered);
            //log.info(counter.missed);
            if (counter.getMissed() == 0) return 100;
            if (counter.getCovered() == 0) return 0;
            int total = counter.getMissed() + counter.getMissed();
            percentage = (int) Math.floor(((double) counter.getCovered() / (double) total) * 100);
            //percentage = (int) (((double) counter.covered / (double) counter.missed) * 100);
            //log.info(percentage);
        } catch (Exception e) {
            log.error(e);
        }
        return percentage;
    }

    public int calculateCoveredBranchesPercent(_Class _class) {
        //TODO: make function throw a custom exception
        if (_class == null) return 0;
        int percentage = 0;
        try {
            Counter counter = _class.getCounter().stream().filter(c -> c.getType().equals("BRANCH")).findFirst().orElseThrow(Exception::new);
            //log.info(counter.covered);
            //log.info(counter.missed);
            if (counter.getMissed() == 0) return 100;
            if (counter.getCovered() == 0) return 0;
            int total = counter.getMissed() + counter.getCovered();
            percentage = (int) Math.floor(((double) counter.getCovered() / (double) total) * 100);
            //log.info(percentage);
        } catch (Exception e) {
            log.error(e);
        }
        return percentage;
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
        return config.getTasks().stream().filter(t -> taskId.equals(t.getId())).findFirst().orElseThrow(TaskNotFoundException::new);
    }
}
