package de.hsrw.dimitriosbarkas.ute.controller.evalutor;

import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.EvaluatorRequestTo;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Log4j2
@RestController
public class EvaluatorController {

    @PostMapping(value = "/api/evaluate")
    public ResponseEntity<?> evaluateTest(@RequestBody EvaluatorRequestTo evaluatorRequestTo) {
        //TODO: Implement
        byte[] decodedBytes = Base64.getDecoder().decode(evaluatorRequestTo.getEncodedTestContent());
        log.info(new String(decodedBytes));
        return null;
    }
}
