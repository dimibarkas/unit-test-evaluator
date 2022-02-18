package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotConvertToFileException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.TaskNotFoundException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface EvaluatorService {

    void evaluateTest(String taskId, String encodedTestContent) throws CannotLoadConfigException, TaskNotFoundException, CannotConvertToFileException, IOException, XMLStreamException, InterruptedException;
}
