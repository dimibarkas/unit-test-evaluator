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


@Service
@Log4j2
public class ConfigServiceImpl implements ConfigService {

    private final String TASKS_DEFINITION_FILE = "tasks.yaml";

    @Override
    public TaskConfig getTaskConfig() throws CannotLoadConfigException {
        log.info("Loading task configuration file from " + TASKS_DEFINITION_FILE + ".");

        URL url = getClass().getClassLoader().getResource(TASKS_DEFINITION_FILE);
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            YamlReader reader = new YamlReader(br);

            TaskConfig taskConfig = reader.read(TaskConfig.class);

            reader.close();

            log.info("Done.");
            return taskConfig;
        } catch (IOException e) {
            throw new CannotLoadConfigException(e);
        }
    }
}
