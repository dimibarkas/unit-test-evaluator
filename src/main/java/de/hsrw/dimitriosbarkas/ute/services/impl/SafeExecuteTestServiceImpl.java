package de.hsrw.dimitriosbarkas.ute.services.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Line;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Sourcefile;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SafeExecuteTestServiceImpl implements SafeExecuteTestService {

    private static final String lines = "------------------------------------------------------------------------";

    @Override
    public Path setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException {
        byte[] testData = Base64.getDecoder().decode(encodedTest);
        byte[] taskData = Base64.getDecoder().decode(task.getEncodedFile());

        Process p;
        try {
            // Create temporary folder
            Path path = Files.createTempDirectory("temp");

            log.info("Creating test environment in temp path...");
            log.info(path.toAbsolutePath());

            // Execute bash script
            String[] command = {"bash", "src/main/resources/create-mvn-project-script.sh", "-p", path.toAbsolutePath().toString()};
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            if(p.exitValue() == 0) {
                log.info(lines);
                log.info("Test environment successfully setup.");
                log.info(lines);
            }

            // write files
            File taskFile = new File(path.toAbsolutePath() + "/testapp/src/main/java/com/test/app/" + task.getPathToFile());
            File testFile = new File(path.toAbsolutePath() + "/testapp/src/test/java/com/test/app/" + task.getPathToTestTemplate());
            writeFile(taskFile, taskData);
            writeFile(testFile, testData);

            return path;
        } catch (IOException | InterruptedException e) {
            throw new CouldNotSetupTestEnvironmentException(e);
        }
    }

    @Override
    public void safelyExecuteTestInTempProject(Path path) throws ErrorWhileExecutingTestException, IOException {
        //test if file is empty
        String[] command = {"mvn", "test"};
        String[] env = {};
        String pathToTempProject = path.toAbsolutePath() + "/testapp";
        File dir = new File(pathToTempProject);
        Process p;
        try {
            p = Runtime.getRuntime().exec(command, env, dir);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("Script output: " + line);
            }
            p.waitFor();
            if (p.exitValue() != 0) {
                throw new ErrorWhileExecutingTestException("BUILD FAILED.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    public Report parseCoverageReport(Path path) throws JacocoReportXmlFileNotFoundException, IOException {
        String pathToReport = path.toAbsolutePath() + "/testapp/target/site/jacoco/jacoco.xml";
        File file = new File(pathToReport);

        Report report;
        try {
            XmlMapper mapper = new XmlMapper();
            String xml = inputStreamToString(new FileInputStream(file));
            report = mapper.readValue(xml, Report.class);
            List<Sourcefile> sourcefileList = report.get_package().getSourcefile();
            List<Line> lineList = sourcefileList
                    .stream()
//                    .filter(s -> s.getName().equals("InsertionSort.java"))
                    .flatMap(s -> s.getLine().stream())
                    .sorted(Comparator.comparingInt(Line::getNr))
                    .collect(Collectors.toList());
            lineList.forEach(System.out::println);
            return report;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * helper method for xml-mapping
     * @param is InputStream
     * @return String from InputStream
     * @throws IOException if an I/O Error occurs
     */
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
            //log.info(file.getAbsolutePath() + " saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
