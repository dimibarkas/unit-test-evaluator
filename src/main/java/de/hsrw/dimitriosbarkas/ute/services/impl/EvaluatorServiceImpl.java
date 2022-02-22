package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;
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
            safeExecuteTestService.safelyExecuteTestInTempProject(path);
            safeExecuteTestService.generateCoverageReport(path);
            report = safeExecuteTestService.parseCoverageReport(path);
            log.info(report);
        } catch (CouldNotSetupTestEnvironmentException |  ErrorWhileExecutingTestException | IOException | InterruptedException  | JacocoReportXmlFileNotFoundException e) {
            log.error(e);
            throw new CompilationErrorException(e);
        }
        return "Test successfully executed.";
    }

    private Task getTaskConfig(String taskId) throws CannotLoadConfigException, TaskNotFoundException {
        TaskConfig config = configService.getTaskConfig();

        //Find task
        return config.getTasks().stream().filter(t -> taskId.equals(t.getId()))
                .findFirst().orElseThrow(TaskNotFoundException::new);
    }
}
