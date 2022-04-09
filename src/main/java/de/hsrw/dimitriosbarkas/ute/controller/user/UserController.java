package de.hsrw.dimitriosbarkas.ute.controller.user;

import de.hsrw.dimitriosbarkas.ute.persistence.user.UserService;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RestController
public class UserController {

    private final UserService userService;

    private final ConfigService configService;

    public UserController(UserService userService, ConfigService configService) {
        this.userService = userService;
        this.configService = configService;
    }

    @PostMapping(value = "/api/user")
    public ResponseEntity<?> createUser() {
        try {
            return new ResponseEntity<>(new UserResponseTO(userService.createUser()), HttpStatus.OK);
        } catch (Error e) {
            log.error(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/api/progress")
    public ResponseEntity<?> getProgressOfUser(@RequestParam String userId) {
        try {
            return new ResponseEntity<>(ProgressTO.fromConfig(configService.getTaskConfig(), userService.getSubmissionsOfUser(UUID.fromString(userId))), HttpStatus.OK);
        } catch (Error | CannotLoadConfigException e) {
            log.error(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

