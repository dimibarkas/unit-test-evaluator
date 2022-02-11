package de.hsrw.dimitriosbarkas.ute.controller.tasks;

import de.hsrw.dimitriosbarkas.ute.controller.tasks.response.TaskConfigResponseTO;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class TaskController {

    @Autowired
    private ConfigService configService;

    @GetMapping(value = "/api/tasks")
    public ResponseEntity<?> getConfig() {
        try {
            return new ResponseEntity<>(
                    TaskConfigResponseTO.fromTaskConfig(configService.getTaskConfig()), HttpStatus.OK);
        } catch (CannotLoadConfigException e) {
            log.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
