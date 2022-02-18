package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotConvertToFileException;

import java.io.IOException;
import java.nio.file.Path;

public interface SafeExecuteTestService {

    Path extractFilesToTemplateProject(Task task, String encodedTest) throws CannotConvertToFileException;

    void safelyExecuteTestInTempProject(Path path) throws IOException;

    void generateCoverageReport(Path path) throws IOException;

}
