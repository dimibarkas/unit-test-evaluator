package de.hsrw.dimitriosbarkas.ute.services.impl;

import com.ctc.wstx.stax.WstxInputFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotConvertToFileException;
import lombok.extern.log4j.Log4j2;
import org.codehaus.plexus.util.xml.XmlStreamReader;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Log4j2
@Service
public class SafeExecuteTestServiceImpl implements SafeExecuteTestService {

    @Override
    public Path extractFilesToTemplateProject(Task task, String encodedTest) throws CannotConvertToFileException {
        byte[] testData = Base64.getDecoder().decode(encodedTest);
        byte[] taskData = Base64.getDecoder().decode(task.getEncodedFile());

        Process p;
        try {
            // Create temporary folder
            Path path = Files.createTempDirectory("temp");

            log.info("Extracting file content to path " + path.toAbsolutePath());

            // Execute bash script
            String[] command = {"bash", "src/main/resources/create-mvn-project-script.sh", "-p", path.toAbsolutePath().toString()};
            p = Runtime.getRuntime().exec(command);

            // Read output from script
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("Script output: " + line);
            }

            reader.close();

            File taskFile = new File(path.toAbsolutePath() + "/testapp/src/main/java/com/test/app/" + task.getPathToFile());
            File testFile = new File(path.toAbsolutePath() + "/testapp/src/test/java/com/test/app/" + task.getPathToTestTemplate());
            writeFile(taskFile, taskData);
            writeFile(testFile, testData);

            //path.toFile().deleteOnExit();

            return path;
        } catch (IOException e) {
            throw new CannotConvertToFileException(e);
        }
    }

    @Override
    public void safelyExecuteTestInTempProject(Path path) throws IOException {
        String[] command = {"mvn", "test"};
        String[] env = {};
        String pathToTempProject = path.toAbsolutePath() + "/testapp";
        File dir = new File(pathToTempProject);
        Process p = Runtime.getRuntime().exec(command, env, dir);

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            log.info("Script output: " + line);
        }

        log.info(dir);
    }

    @Override
    public void generateCoverageReport(Path path) throws IOException, InterruptedException {
        String[] command = {"mvn", "jacoco:report"};
        String[] env = {};
        String pathToTempProject = path.toAbsolutePath() + "/testapp";
        File dir = new File(pathToTempProject);
        Process p = Runtime.getRuntime().exec(command, env, dir);
        p.waitFor();
    }

    @Override
    public void readCoverageReport(Path path) throws IOException, XMLStreamException {
        String pathToReport = path.toAbsolutePath() + "/testapp/target/site/jacoco/jacoco.xml";
        File file = new File(pathToReport);

        Report report;
        if(file.exists()) {
            XmlMapper mapper = new XmlMapper();
            String xml = inputStreamToString(new FileInputStream(file));
            report = mapper.readValue(xml, Report.class);
            log.info(report);
        }

    }

    private String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }


    private void writeFile(File file, byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            String str = "package com.test.app; \n\n";
            byte[] strToBytes = str.getBytes();
            fos.write(strToBytes);
            fos.write(data);
            log.info(file.getAbsolutePath() + " saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
