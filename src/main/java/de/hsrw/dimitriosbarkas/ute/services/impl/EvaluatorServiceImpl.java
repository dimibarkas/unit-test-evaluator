package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.TaskNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EvaluatorServiceImpl implements EvaluatorService {

    @Autowired
    private ConfigService configService;

    @Override
    public void evaluateTest(String taskId, String encodedTestContent) throws CannotLoadConfigException, TaskNotFoundException {
        log.info("Evaluating test for task" + taskId + "...");

        // Get configuration for this task
        Task task = getTaskConfig(taskId);


    }

    private Task getTaskConfig(String taskId) throws CannotLoadConfigException, TaskNotFoundException {
        TaskConfig config = configService.getTaskConfig();

        //Find task
        return config.getTasks().stream().filter(t -> taskId.equals(t.getId()))
                .findFirst().orElseThrow(() -> new TaskNotFoundException());
    }
}
