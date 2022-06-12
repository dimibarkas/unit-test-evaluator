package de.hsrw.dimitriosbarkas.ute.services.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Line;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Sourcefile;
import de.hsrw.dimitriosbarkas.ute.model.pitest.MutationReport;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.hsrw.dimitriosbarkas.ute.utils.Utilities.*;

@Log4j2
@Service
public class SafeExecuteTestServiceImpl implements SafeExecuteTestService {


    @Override
    public Path setupTestEnvironment(Task task, String encodedTest) throws CouldNotSetupTestEnvironmentException {

        //TODO: bevor der Test überhaupt in die Testumgebung geschrieben wird, soll geprüft werden ob das Template verändert wurde...
        // wenn ja, dann soll ebenfalls eine Custom-Exception geworden werden. TestTemplateCorruptedException
        byte[] testData = Base64.getDecoder().decode(encodedTest);

        Path path;
        Process p;
        try {
            // Create temporary folder
            path = Files.createTempDirectory("temp");

            log.info("Creating test environment in temp path ...");
            log.info(path.toAbsolutePath());

            URL scriptResourceUrl = this.getClass().getClassLoader().getResource("scripts/create-mvn-project-script.sh");
            URL pomTemplateResourceUrl = this.getClass().getClassLoader().getResource("templates/pom.xml");

            File bashScriptFile = File.createTempFile("temp", "script.sh");
            FileUtils.copyURLToFile(scriptResourceUrl, bashScriptFile);

            File pomTemplateFile = File.createTempFile("temp", "pomTemplate.xml");
            FileUtils.copyURLToFile(pomTemplateResourceUrl, pomTemplateFile);

            // Execute bash script
            String[] command = {"bash", bashScriptFile.getAbsolutePath(), "-p", path.toAbsolutePath().toString(), "-x", pomTemplateFile.getAbsolutePath()};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            File logfile = new File("error-log.txt");
            processBuilder.redirectOutput(logfile);
            p = processBuilder.start();
            p.waitFor();

            if (p.exitValue() != 0) {
                throw new CouldNotSetupTestEnvironmentException("Error while creating test environment");
            }

            if (task.getMutators().isEmpty()) {
                log.info("no custom mutators found, using default ones.");
            } else {
                //add custom mutators to pom file
                log.info("Using custom mutators: " + task.getMutators());
                File pomFile = new File(path.toAbsolutePath() + "/testapp/pom.xml");

//                log.info(pomFile.getAbsolutePath());
                if (pomFile.exists()) {
                    addMutators(pomFile, task.getMutators());
                }
            }

            log.info("Test environment successfully setup.");

            //delete temporary files
            if (bashScriptFile.delete()) {
                log.info("temp script file deleted.");
            }

            if (pomTemplateFile.delete()) {
                log.info("temp pom template file deleted.");
            }


            // write files to test environment
            loadSourceFiles(task, path);

//            File taskFile = new File(path.toAbsolutePath() + "/testapp/src/main/java/com/test/app/" + task.getSourcefilename());
//            writeFile(taskFile, taskData);

            log.info("Sourcefiles successfully written.");

            File testFile = new File(path.toAbsolutePath() + "/testapp/src/test/java/com/test/app/" + task.getTesttemplatefilename());
            writeFile(testFile, testData);

            log.info("Testfile successfully written.");


            return path;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new CouldNotSetupTestEnvironmentException(e);
        }
    }

    private void copyFromResource(File file, String resource) throws IOException {
        URL dirURL = getClass().getClassLoader().getResource(resource);
        FileUtils.copyURLToFile(dirURL, file);
    }


    /**
     * This function loads all the source code in to the current temporary user working directory.
     * @param task the selected task
     * @param tempDir the temporary user working directory
     * @throws IOException if the copy of the file goes wrong
     */
    private void loadSourceFiles(Task task, Path tempDir) throws IOException {
        //add optional exception classes
        if(!task.getAdditionalFiles().isEmpty()) {
            task.getAdditionalFiles().forEach(file -> {
                File exceptionFile = new File(tempDir.toAbsolutePath() + "/testapp/src/main/java/com/test/app/" + file);
                try {
                    copyFromResource(exceptionFile,task.getPathToDir() + file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        File taskFile = new File(tempDir.toAbsolutePath() + "/testapp/src/main/java/com/test/app/" + task.getSourcefilename());
        copyFromResource(taskFile, task.getPathToDir()+ task.getSourcefilename());
    }

    private boolean checkIfTestsWereCorrupted(String encodedTestTemplate) {
        //TODO: Implement
        return true;
    }

    @Override
    public SubmissionResult buildAndRunTests(Task task, Path path) throws ErrorWhileExecutingTestException {

        //prepare command and choose right directory
        String[] command = {"mvn", "clean", "test"};
        String[] env = {};
        String pathToTempProject = path.toAbsolutePath() + "/testapp";
        File dir = new File(pathToTempProject);

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(dir);
        pb.redirectErrorStream(true);

        //execute test in different thread
        Process process;
        try {
            process = pb.start();
            process.waitFor();

            // check if build was successful
            if (process.exitValue() == 0) {
                return getSuccessfulTestResult(task, path);
            }
            return getBuildOrTestErrors(process, path, task);

        } catch (IOException | InterruptedException | ErrorWhileGeneratingCoverageReport |
                 JacocoReportXmlFileNotFoundException | ErrorWhileParsingReportException e) {
            throw new ErrorWhileExecutingTestException(e);
        }
    }


    private SubmissionResult getSuccessfulTestResult(Task task, Path path) throws
            ErrorWhileGeneratingCoverageReport, JacocoReportXmlFileNotFoundException, ErrorWhileParsingReportException {
        log.info("Build successful");
        String filename = task.getTesttemplatefilename().replace(".java", ".txt");
        String pathToFile = path + "/testapp/target/surefire-reports/com.test.app." + filename;
        File file = new File(pathToFile);
        String output = readFromFile(file);
        generateCoverageReport(path);
        generateMutationCoverageReport(path);
        Report report = parseCoverageReport(path);
        MutationReport mutationReport = parseMutationCoverageReport(path);
        return new SubmissionResult(output, report, mutationReport, BuildSummary.BUILD_SUCCESSFUL, null);
    }


    private SubmissionResult getBuildOrTestErrors(Process process, Path path, Task task) throws
            IOException, InterruptedException {
        String pathToDir = path.toAbsolutePath() + "/testapp/target/surefire-reports/";
        boolean buildSucceed = new File(pathToDir).exists();
        if (buildSucceed) {
            log.warn("Build successful - but there are test failures");
            String filename = task.getTesttemplatefilename().replace(".java", ".txt");
            String pathToFile = path + "/testapp/target/surefire-reports/com.test.app." + filename;
            File file = new File(pathToFile);
            String output = readFromFile(file);
            return new SubmissionResult(output, null, null, BuildSummary.TESTS_FAILED, null);
        }

        log.error("Build failed");
        // return error logs
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader((new InputStreamReader(process.getInputStream())))) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[ERROR]")) {
                    sb.append(line);
                    sb.append("\n");
                }
            }
        }
        return new SubmissionResult(sb.toString(), null, null, BuildSummary.BUILD_FAILED, null);
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
    public void generateMutationCoverageReport(Path path) {
        try {
            String[] command = {"mvn", "pitest:mutationCoverage"};
            String[] env = {};
            String pathToTempProject = path.toAbsolutePath() + "/testapp";
            File dir = new File(pathToTempProject);
            Process p = Runtime.getRuntime().exec(command, env, dir);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new Error(e);
        }
    }

    @Override
    public Report parseCoverageReport(Path path) throws
            JacocoReportXmlFileNotFoundException, ErrorWhileParsingReportException {
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

    @Override
    public MutationReport parseMutationCoverageReport(Path path) throws ErrorWhileParsingReportException {
        String pathToReport = path.toAbsolutePath() + "/testapp/target/pit-reports";
        File pitTestDirectory = new File(pathToReport);
        if (!pitTestDirectory.exists()) {
            //TODO: create custom exception
            String errorMessage = "something went wrong " + path;
            throw new Error(errorMessage);
        }
        Optional<File> fileContainingMutationCoverage = Arrays.stream(Objects.requireNonNull(pitTestDirectory.listFiles())).findFirst();

        if (fileContainingMutationCoverage.isEmpty()) {
            String errorMessage = "no mutation coverage report found" + path;
            throw new Error(errorMessage);
        }

        MutationReport mutationReport;
        try {
            XmlMapper mapper = new XmlMapper();
            String xml = inputStreamToString(new FileInputStream(fileContainingMutationCoverage.get() + "/mutations.xml"));
            mutationReport = mapper.readValue(xml, MutationReport.class);
            return mutationReport;
        } catch (IOException e) {
            throw new ErrorWhileParsingReportException(e);
        }
    }


}
