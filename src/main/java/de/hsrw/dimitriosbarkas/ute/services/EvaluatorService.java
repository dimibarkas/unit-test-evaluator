package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.TestResult;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;

public interface EvaluatorService {

    /**
     * This function takes the taskId and creates a testing environment to execute the encoded test file and returns an assumption.
     * @param taskId the id for the specified test
     * @param encodedTestContent the base 64 encoded test file
     * @return assumption of execution as a string
     * @throws CannotLoadConfigException if config file cannot be found
     * @throws TaskNotFoundException if the specified task cannot be found
     * @throws CompilationErrorException if an error occurred during the compilation
     */
    TestResult evaluateTest(String taskId, String encodedTestContent)
            throws CannotLoadConfigException, TaskNotFoundException, CompilationErrorException;

}
