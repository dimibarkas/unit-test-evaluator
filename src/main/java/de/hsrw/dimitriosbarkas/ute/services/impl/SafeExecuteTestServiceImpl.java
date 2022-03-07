package de.hsrw.dimitriosbarkas.ute.services.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.TestResult;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Line;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Sourcefile;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static de.hsrw.dimitriosbarkas.ute.utils.Utilities.*;

@Log4j2
@Service
@Data
public class SafeExecuteTestServiceImpl implements SafeExecuteTestService {

    private Path path;
    private Task task;

    @Override
    public void setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException {
        setTask(task);
        byte[] testData = Base64.getDecoder().decode(encodedTest);
        byte[] taskData = Base64.getDecoder().decode(task.getEncodedFile());

        Process p;
        try {
            // Create temporary folder
            setPath(Files.createTempDirectory("temp"));

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

            log.info("Tests successfully written.");

        } catch (IOException | InterruptedException e) {
            throw new CouldNotSetupTestEnvironmentException(e);
        }
    }

    @Override
    public TestResult buildAndRunTests() throws ErrorWhileExecutingTestException {

        //prepare command and choose right directory
        String[] command = {"mvn", "clean", "test"};
        String[] env = {};
        String pathToTempProject = path.toAbsolutePath() + "/testapp";
        File dir = new File(pathToTempProject);

        //execute test in different thread
        Process process;
        StringBuilder sb;
        try {
            process = Runtime.getRuntime().exec(command, env, dir);
            process.waitFor();

            // check if build was successful
            if (process.exitValue() == 0) return getSuccessfulTestResult(path);
            return getBuildOrTestErrors(process, path);

        } catch (IOException | InterruptedException | ErrorWhileGeneratingCoverageReport | JacocoReportXmlFileNotFoundException | ErrorWhileParsingReportException e) {
            throw new ErrorWhileExecutingTestException(e);
        }
    }


    private TestResult getSuccessfulTestResult(Path path) throws ErrorWhileGeneratingCoverageReport, JacocoReportXmlFileNotFoundException, ErrorWhileParsingReportException {
        log.info("build successful");
        String filename = task.getPathToTestTemplate().replace(".java", ".txt");
        String pathToFile = path + "/testapp/target/surefire-reports/com.test.app." + filename;
        File file = new File(pathToFile);
        String output = readFromFile(file);
        generateCoverageReport(path);
        Report report = parseCoverageReport(path);
        return new TestResult(output, report, BuildSummary.BUILD_SUCCESSFUL);
    }


    private TestResult getBuildOrTestErrors(Process process, Path path) throws IOException, InterruptedException {
        String pathToDir = path.toAbsolutePath() + "/testapp/target/surefire-reports/";
        boolean buildSucceed = new File(pathToDir).exists();
        if(buildSucceed) {
            log.warn("build successful - but there are test failures");
            String filename = task.getPathToTestTemplate().replace(".java", ".txt");
            String pathToFile = path + "/testapp/target/surefire-reports/com.test.app." + filename;
            File file = new File(pathToFile);
            String output = readFromFile(file);
            return new TestResult(output, null, BuildSummary.TESTS_FAILED);
        }

        log.error("build failed");
        // return error logs
        StringBuilder sb = new StringBuilder();
        String line;
        try(BufferedReader reader = new BufferedReader((new InputStreamReader(process.getInputStream())))) {
            while((line = reader.readLine()) != null) {
                if(line.startsWith("[ERROR]")) {
                    sb.append(line);
                    sb.append("\n");
                }
            }
        }
        return new TestResult(sb.toString(), null, BuildSummary.BUILD_FAILED);
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

}
