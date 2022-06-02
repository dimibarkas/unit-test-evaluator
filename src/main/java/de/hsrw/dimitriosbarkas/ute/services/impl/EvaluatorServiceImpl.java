package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.SubmissionTO;
import de.hsrw.dimitriosbarkas.ute.model.*;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Counter;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport._Class;
import de.hsrw.dimitriosbarkas.ute.model.pitest.Mutation;
import de.hsrw.dimitriosbarkas.ute.model.pitest.MutationReport;
import de.hsrw.dimitriosbarkas.ute.persistence.student.Student;
import de.hsrw.dimitriosbarkas.ute.persistence.student.StudentService;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.FeedbackService;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class EvaluatorServiceImpl implements EvaluatorService {

    private final ConfigService configService;

    private final SafeExecuteTestService safeExecuteTestService;

    private final StudentService studentService;

    private final FeedbackService feedbackService;

    public EvaluatorServiceImpl(ConfigService configService, SafeExecuteTestService safeExecuteTestService, StudentService studentService, FeedbackService feedbackService) {
        this.configService = configService;
        this.safeExecuteTestService = safeExecuteTestService;
        this.studentService = studentService;
        this.feedbackService = feedbackService;
    }

    @Override
    public SubmissionResult evaluateTest(Long studentId, SubmissionTO submissionTO) throws CannotLoadConfigException, TaskNotFoundException, CompilationErrorException {
        log.info("Evaluating test for task " + submissionTO.getTaskId() + " ...");

        // Get configuration for this task
        Task task = getTaskConfig(submissionTO.getTaskId());

        SubmissionResult currentSubmissionResult;
        try {
            safeExecuteTestService.setupTestEnvironment(task, submissionTO.getEncodedTestContent());
            currentSubmissionResult = safeExecuteTestService.buildAndRunTests();

            if (currentSubmissionResult.getSummary() == BuildSummary.BUILD_SUCCESSFUL) {
                boolean allMutationsPassed = false;
                List<Mutation> mutationList = getMutationResult(currentSubmissionResult.getMutationReport(), task);
                if(mutationList.isEmpty()) allMutationsPassed = true;
                CoverageResult coverageResult = getCoverageResult(currentSubmissionResult.getReport(), task);
                studentService.addSubmission(studentId,
                        submissionTO.getTaskId(),
                        coverageResult.getCoveredInstructions(),
                        coverageResult.getCoveredBranches(),
                        currentSubmissionResult.getSummary(),
                        allMutationsPassed);
            } else {
                studentService.addSubmission(studentId,
                        submissionTO.getTaskId(),
                        0,
                        0,
                        currentSubmissionResult.getSummary(),
                        false);
            }

            try {
                Student student = studentService.getStudentById(studentId);
                currentSubmissionResult.setFeedback(feedbackService.provideFeedback(student, task, currentSubmissionResult));
            } catch (SourcefileNotFoundException e) {
                log.error(e.getMessage());
            } catch (NoHintProvidedException e) {
                String errorMessage = String.format("No hint provided for line: %d", e.getLineNumber());
                log.error(errorMessage);
            } catch (NoFeedbackFoundException e) {
                log.error("The feedback list for this kind of errors is empty.");
            }

            return currentSubmissionResult;
        } catch (CouldNotSetupTestEnvironmentException | ErrorWhileExecutingTestException e) {
            log.error(e);
            throw new CompilationErrorException(e);
        }
    }
    @Override
    public List<Mutation> getMutationResult(MutationReport mutationReport, Task task) {
        return mutationReport
                .getMutations()
                .stream()
                .filter(mutation -> mutation.getSourceFile().equals(task.getSourcefilename())
                        && !mutation.isDetected())
                .collect(Collectors.toList());
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
            if (counter.getMissed() == 0) return 100;
            if (counter.getCovered() == 0) return 0;
            int total = counter.getMissed() + counter.getCovered();
            percentage = (int) Math.floor(((double) counter.getCovered() / (double) total) * 100);
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
            if (counter.getMissed() == 0) return 100;
            if (counter.getCovered() == 0) return 0;
            int total = counter.getMissed() + counter.getCovered();
            percentage = (int) Math.floor(((double) counter.getCovered() / (double) total) * 100);
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
