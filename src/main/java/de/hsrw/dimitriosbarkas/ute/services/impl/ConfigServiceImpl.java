package de.hsrw.dimitriosbarkas.ute.services.impl;

import com.esotericsoftware.yamlbeans.YamlReader;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URL;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;


@Service
@Log4j2
public class ConfigServiceImpl implements ConfigService {

    public static String readFileToString(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        return asString(resource);
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public TaskConfig getTaskConfig() throws CannotLoadConfigException {
        String TASKS_DEFINITION_FILE = "tasks.yaml";
        log.info("Loading task configuration file from " + TASKS_DEFINITION_FILE + ".");

        URL url = getClass().getClassLoader().getResource(TASKS_DEFINITION_FILE);
        if (url == null) throw new CannotLoadConfigException();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8))) {
            YamlReader reader = new YamlReader(br);

            TaskConfig taskConfig = reader.read(TaskConfig.class);

            taskConfig.getTasks().forEach(task -> {
                String pathToFile = task.getPathToDir() + task.getSourcefilename();
                String pathToTemplate = task.getPathToDir() + task.getTesttemplatefilename();
                String fileContent = readFileToString(pathToFile);
                if (!fileContent.isEmpty()) {
                    String encodedFile = Base64.getEncoder().encodeToString(fileContent.getBytes(UTF_8));
                    task.setEncodedFile(encodedFile);
                }
                String templateContent = readFileToString(pathToTemplate);
                if (!templateContent.isEmpty()) {
                    String encodedTemplate = Base64.getEncoder().encodeToString(templateContent.getBytes(UTF_8));
                    task.setEncodedTestTemplate(encodedTemplate);
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
