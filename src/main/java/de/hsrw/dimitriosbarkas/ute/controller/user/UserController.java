package de.hsrw.dimitriosbarkas.ute.controller.user;

import de.hsrw.dimitriosbarkas.ute.persistence.student.Student;
import de.hsrw.dimitriosbarkas.ute.persistence.student.StudentService;
import de.hsrw.dimitriosbarkas.ute.services.ConfigService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import de.hsrw.dimitriosbarkas.ute.utils.ErrorResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class UserController {

    private final StudentService studentService;

    private final ConfigService configService;

    public UserController(StudentService studentService, ConfigService configService) {
        this.studentService = studentService;
        this.configService = configService;
    }

    @GetMapping(value = "/api/{studentId}/progress")
    public ResponseEntity<?> getProgressOfUser(@PathVariable("studentId") Long studentId, @RequestParam String authKey) {

        if (studentId == null || authKey == null) {
            return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, "Ungültige Anfrage");
        }

        //Get student
        Student student;
        student = studentService.getStudentById(studentId);
        if (student == null) {
            log.error("Task Controller status (failed): Student with ID " + studentId + " could not be found.");
            return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, "Ungültige Anfrage.");
        }

        try {
            return new ResponseEntity<>(ProgressTO.fromConfig(configService.getTaskConfig(), studentService.listSubmissionsOfStudent(studentId)), HttpStatus.OK);
        } catch (Error | CannotLoadConfigException e) {
            log.error(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

