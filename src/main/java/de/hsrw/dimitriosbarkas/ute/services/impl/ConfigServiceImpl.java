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

    public String loadResourceByUrl(URL u) throws IOException {
        if (u == null) return null;
        return new String(Files.readAllBytes(Paths.get(u.getPath())));
    }

    @Override
    public TaskConfig getTaskConfig() throws CannotLoadConfigException {
        String TASKS_DEFINITION_FILE = "tasks.yaml";
        log.info("Loading task configuration file from " + TASKS_DEFINITION_FILE + ".");

        URL url = getClass().getClassLoader().getResource(TASKS_DEFINITION_FILE);
        if (url == null) throw new CannotLoadConfigException();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            YamlReader reader = new YamlReader(br);

            TaskConfig taskConfig = reader.read(TaskConfig.class);

            taskConfig.getTasks().forEach(task -> {
                String pathToFile = task.getPathToDir() + task.getSourcefilename();
                String pathToTemplate = task.getPathToDir() + task.getTesttemplatefilename();
                URL fileURL = getClass().getClassLoader().getResource(pathToFile);
                URL templateURL = getClass().getClassLoader().getResource(pathToTemplate);
                try {
                    String fileContent = loadResourceByUrl(fileURL);
                    if (fileContent != null) {
                        String encodedFile = Base64.getEncoder().encodeToString(fileContent.getBytes(StandardCharsets.UTF_8));
                        task.setEncodedFile(encodedFile);
                    }
                    String templateContent = loadResourceByUrl(templateURL);
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
