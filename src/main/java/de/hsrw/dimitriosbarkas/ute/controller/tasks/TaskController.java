package de.hsrw.dimitriosbarkas.ute.controller.tasks;

import de.hsrw.dimitriosbarkas.ute.controller.tasks.response.TaskConfigResponseTO;
import de.hsrw.dimitriosbarkas.ute.persistence.student.Student;
import de.hsrw.dimitriosbarkas.ute.persistence.student.StudentService;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import de.hsrw.dimitriosbarkas.ute.utils.ErrorResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
public class TaskController {

    private final ConfigService configService;

    private final StudentService studentService;

    public TaskController(ConfigService configService, StudentService studentService) {
        this.configService = configService;
        this.studentService = studentService;
    }

    @GetMapping(value = "/api/{studentId}/tasks")
    public ResponseEntity<?> getConfig(@PathVariable("studentId") Long studentId, @RequestParam String authKey) {

        if (studentId == null || authKey == null) {
            return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, "Ungültige Anfrage");
        }

        //Get student
        Student student;
        student = studentService.getStudentById(studentId);
        if (student == null) {
            log.error("Get review status (failed): Student ID " + studentId + ". Cannot load configuration file.");
            return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, "Ungültige Anfrage.");
        }


        if(!authKey.equals(student.getAuthKey())) {
            log.error("Task Controller status (failed): Student ID " + studentId + ". Wrong auth key.");
            return ErrorResponseUtil.build(HttpStatus.FORBIDDEN, "Zugriff verweigert.");
        }

        try {
            return new ResponseEntity<>(
                    TaskConfigResponseTO.fromTaskConfig(configService.getTaskConfig()), HttpStatus.OK);
        } catch (CannotLoadConfigException e) {
            log.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
