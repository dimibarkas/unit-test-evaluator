package de.hsrw.dimitriosbarkas.ute.services.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TestResult;
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

    @Override
    public Path setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException {
        byte[] testData = Base64.getDecoder().decode(encodedTest);
        byte[] taskData = Base64.getDecoder().decode(task.getEncodedFile());

        Process p;
        try {
            // Create temporary folder
            Path path = Files.createTempDirectory("temp");

            log.info("Creating test environment in temp path ...");
            log.info(path.toAbsolutePath());

            // Execute bash script
            String[] command = {"bash", "src/main/resources/create-mvn-project-script.sh", "-p", path.toAbsolutePath().toString()};
            p = Runtime.getRuntime().exec(command);
            p.waitFor();

            if (p.exitValue() != 0) {
                throw new CouldNotSetupTestEnvironmentException("Error while creating test environment");
            }

            log.info("Test environment successfully setup.");

            // write files
            File taskFile = new File(path.toAbsolutePath() + "/testapp/src/main/java/com/test/app/" + task.getPathToFile());
            File testFile = new File(path.toAbsolutePath() + "/testapp/src/test/java/com/test/app/" + task.getPathToTestTemplate());
            writeFile(taskFile, taskData);
            writeFile(testFile, testData);

            log.info("Write files to test environment.");

            return path;
        } catch (IOException | InterruptedException e) {
            throw new CouldNotSetupTestEnvironmentException(e);
        }
    }

    @Override
    public TestResult executeTestInTempDirectory(Path path) throws ErrorWhileExecutingTestException {

        //prepare command and choose right directory
        String[] command = {"mvn", "clean", "test"};
        String[] env = {};
        String pathToTempProject = path.toAbsolutePath() + "/testapp";
        File dir = new File(pathToTempProject);

        //execute test in different thread
        Process p;
        StringBuilder sb;
        try {
            p = Runtime.getRuntime().exec(command, env, dir);
            p.waitFor();

            sb = new StringBuilder();
            // TODO: if possible, just read the error messages.. maybe with ErrorStream or with a filter inside a stream
            // like this:
            // [ERROR] /private/var/folders/yq/xv6h8nj97tzcqs9dnp8zgknr0000gn/T/temp15456590090410174899/testapp/src/test/java/com/test/app/InsertionSortTest.java:[12,23] '}' expected
            // [ERROR] /private/var/folders/yq/xv6h8nj97tzcqs9dnp8zgknr0000gn/T/temp15456590090410174899/testapp/src/test/java/com/test/app/InsertionSortTest.java:[13,9] invalid method declaration; return type required
            // [ERROR] /private/var/folders/yq/xv6h8nj97tzcqs9dnp8zgknr0000gn/T/temp15456590090410174899/testapp/src/test/java/com/test/app/InsertionSortTest.java:[15,1] class, interface, or enum expected
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return new TestResult(sb.toString(), p.exitValue(), null, null);
        } catch (IOException | InterruptedException e) {
            throw new ErrorWhileExecutingTestException(e);
        }
    }


    @Override
    public void generateCoverageReport(Path path) throws ErrorWhileGeneratingCoverageReport {
        try {
            String[] command = {"mvn", "jacoco:report"};
            String[] env = {};
            String pathToTempProject = path.toAbsolutePath() + "/testapp";
            File dir = new File(pathToTempProject);
            Process p = Runtime.getRuntime().exec(command, env, dir);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new ErrorWhileGeneratingCoverageReport(e);
        }

    }

    @Override
    public Report parseCoverageReport(Path path) throws JacocoReportXmlFileNotFoundException, ErrorWhileParsingReportException {
        String pathToReport = path.toAbsolutePath() + "/testapp/target/site/jacoco/jacoco.xml";
        File file = new File(pathToReport);

        if (!file.exists()) {
            String errorMessage = "The file jacoco.xml could not be found in path " + path;
            throw new JacocoReportXmlFileNotFoundException(errorMessage);
        }

        Report report;
        try {
            XmlMapper mapper = new XmlMapper();
            String xml = inputStreamToString(new FileInputStream(file));
            report = mapper.readValue(xml, Report.class);
            List<Sourcefile> sourcefileList = report.get_package().getSourcefile();
            List<Line> lineList = sourcefileList.stream()
//                    .filter(s -> s.getName().equals("InsertionSort.java"))
                    .flatMap(s -> s.getLine().stream()).sorted(Comparator.comparingInt(Line::getNr)).collect(Collectors.toList());
//            lineList.forEach(System.out::println);
            return report;
        } catch (IOException e) {
            throw new ErrorWhileParsingReportException(e);
        }
    }

    /**
     * helper method for xml-mapping
     *
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
