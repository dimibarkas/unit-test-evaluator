package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.TaskNotFoundException;

public interface EvaluatorService {

    void evaluateTest(String taskId, String encodedTestContent) throws CannotLoadConfigException, TaskNotFoundException;
}
