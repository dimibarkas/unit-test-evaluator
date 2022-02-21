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
    public void evaluateTest(String taskId, String encodedTestContent) throws CannotLoadConfigException, TaskNotFoundException, CannotConvertToFileException, IOException, XMLStreamException, InterruptedException, JacocoReportXmlFileNotFoundException, CompliationErrorException {
        log.info("Evaluating test for task" + taskId + "...");

        // Get configuration for this task
        Task task = getTaskConfig(taskId);



        // TODO: surround with try catch -> if exception is thrown, then throw an eval custom exception (EvaluationException)
        Path path = safeExecuteTestService.extractFilesToTemplateProject(task, encodedTestContent);
        safeExecuteTestService.safelyExecuteTestInTempProject(path);
        safeExecuteTestService.generateCoverageReport(path);
        safeExecuteTestService.parseCoverageReport(path);
    }

    private Task getTaskConfig(String taskId) throws CannotLoadConfigException, TaskNotFoundException {
        TaskConfig config = configService.getTaskConfig();

        //Find task
        return config.getTasks().stream().filter(t -> taskId.equals(t.getId()))
                .findFirst().orElseThrow(TaskNotFoundException::new);
    }
}
