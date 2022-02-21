package de.hsrw.dimitriosbarkas.ute.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotConvertToFileException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CompliationErrorException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.JacocoReportXmlFileNotFoundException;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface SafeExecuteTestService {

    Path extractFilesToTemplateProject(Task task, String encodedTest) throws CannotConvertToFileException;

    void safelyExecuteTestInTempProject(Path path) throws IOException, CompliationErrorException;

    void generateCoverageReport(Path path) throws IOException, InterruptedException;

    Report parseCoverageReport(Path path) throws FileNotFoundException, XMLStreamException, JsonProcessingException, JacocoReportXmlFileNotFoundException;

}
