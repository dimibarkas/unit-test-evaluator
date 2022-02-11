package de.hsrw.dimitriosbarkas.ute.services.impl;

import com.esotericsoftware.yamlbeans.YamlReader;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


@Service
@Log4j2
public class ConfigServiceImpl implements ConfigService {

    private final String TASKS_DEFINITION_FILE = "tasks.yaml";

    public String loadResourceByUrl(URL u, String resource) throws IOException {
        log.info("attempting input resource", resource);
        if (u != null) {
            String path = u.getPath();
            log.info(" absolute resource path found: " + path);
            String s = new String(Files.readAllBytes(Paths.get(path)));
//            log.info(" file content: \n"+s);
            return s;
        } else {
            log.info(" no resource found: " + resource);
            return null;
        }
    }

    @Override
    public TaskConfig getTaskConfig() throws CannotLoadConfigException {
        log.info("Loading task configuration file from " + TASKS_DEFINITION_FILE + ".");

        URL url = getClass().getClassLoader().getResource(TASKS_DEFINITION_FILE);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            YamlReader reader = new YamlReader(br);

            TaskConfig taskConfig = reader.read(TaskConfig.class);

            taskConfig.getTasks().stream().forEach(task -> {
                String pathToFile = task.getPathToFile();
                URL fileURL = getClass().getClassLoader().getResource(pathToFile);
                String pathToTemplate = task.getPathToTestTemplate();
                URL templateURL = getClass().getClassLoader().getResource(pathToTemplate);
                try {
                    String fileContent = loadResourceByUrl(fileURL, pathToFile);
                    if (fileContent != null) {
                        String encodedFile = Base64.getEncoder().encodeToString(fileContent.getBytes(StandardCharsets.UTF_8));
                        task.setEncodedFile(encodedFile);
                    }
                    String templateContent = loadResourceByUrl(templateURL, pathToTemplate);
                    if (templateContent != null) {
                        String encodedTemplate = Base64.getEncoder().encodeToString(templateContent.getBytes(StandardCharsets.UTF_8));
                        task.setEncodedTestTemplate(encodedTemplate);
                    }
                } catch (IOException e) {
                    log.error(e);
                }
            });

            reader.close();

            log.info("Done.");
            return taskConfig;
        } catch (IOException e) {
            throw new CannotLoadConfigException(e);
        }
    }
}
