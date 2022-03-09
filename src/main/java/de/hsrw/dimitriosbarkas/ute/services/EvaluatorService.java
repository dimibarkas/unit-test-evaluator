package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.SubmissionTO;
import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;

/**
 * This interfaces provides the main method that is executed when the evaluator controller is called.
 */
public interface EvaluatorService {

    /**
     * This function takes the taskId and creates a testing environment to execute the encoded test file and returns an assumption.
     *
     * @param submissionTO the submission to be evaluated
     * @return TestResult assumption of execution
     * @throws CannotLoadConfigException if config file cannot be found
     * @throws TaskNotFoundException     if the specified task cannot be found
     * @throws CompilationErrorException if an error occurred during the compilation
     */
    SubmissionResult evaluateTest(SubmissionTO submissionTO) throws CannotLoadConfigException, TaskNotFoundException, CompilationErrorException;

}
