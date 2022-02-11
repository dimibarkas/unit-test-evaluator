package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;

import java.io.IOException;
import java.net.URL;

public interface ConfigService {
    /**
     * Returns task configuration.
     * @throws CannotLoadConfigException if configuration cannot be loaded.
     */
    TaskConfig getTaskConfig() throws CannotLoadConfigException;
}
