package de.hsrw.dimitriosbarkas.ute.controller.evalutor;

import de.hsrw.dimitriosbarkas.ute.common.ErrorResultTO;
import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.SubmissionTO;
import de.hsrw.dimitriosbarkas.ute.persistence.student.Student;
import de.hsrw.dimitriosbarkas.ute.persistence.student.StudentService;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import de.hsrw.dimitriosbarkas.ute.utils.ErrorResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class EvaluatorController {

    private final EvaluatorService evaluatorService;

    private final StudentService studentService;

    public EvaluatorController(EvaluatorService evaluatorService, StudentService studentService) {
        this.evaluatorService = evaluatorService;
        this.studentService = studentService;
    }

    @PostMapping(value = "/api/{studentId}/evaluate")
    public ResponseEntity<?> evaluateTest(@PathVariable("studentId") Long studentId, @RequestParam String authKey, @RequestBody SubmissionTO submissionTO) {

        if (studentId == null || authKey == null) {
            return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, "Ungültige Anfrage");
        }

        //Get student
        Student student;
        student = studentService.getStudentById(studentId);
        if (student == null) {
            log.error("Evaluator Controller status (failed): Student with ID " + studentId + " could not be found.");
            return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, "Ungültige Anfrage.");
        }

        if(!authKey.equals(student.getAuthKey())) {
            log.error("Evaluator Controller status (failed): Student ID " + studentId + ". Wrong auth key.");
            return ErrorResponseUtil.build(HttpStatus.FORBIDDEN, "Zugriff verweigert.");
        }


        try {
            return new ResponseEntity<>(
                    evaluatorService.evaluateTest(studentId, submissionTO),
                    HttpStatus.OK);
        } catch (TaskNotFoundException | CompilationErrorException | CannotLoadConfigException e) {
            log.error(e);
            return new ResponseEntity<>(new ErrorResultTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
