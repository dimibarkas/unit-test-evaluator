package de.hsrw.dimitriosbarkas.ute.controller.evalutor;

import de.hsrw.dimitriosbarkas.ute.common.ErrorResultTO;
import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.Submission;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class EvaluatorController {

    private final EvaluatorService evaluatorService;

    public EvaluatorController(EvaluatorService evaluatorService) {
        this.evaluatorService = evaluatorService;
    }

    @PostMapping(value = "/api/evaluate")
    public ResponseEntity<?> evaluateTest(@RequestBody Submission submission) {
        try {
            return new ResponseEntity<>(
                    evaluatorService.evaluateTest(submission),
                    HttpStatus.OK);
        } catch (TaskNotFoundException | CompilationErrorException | CannotLoadConfigException e) {
            log.error(e);
            return new ResponseEntity<>(new ErrorResultTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
