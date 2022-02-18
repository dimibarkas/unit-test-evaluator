package de.hsrw.dimitriosbarkas.ute.controller.evalutor;

import de.hsrw.dimitriosbarkas.ute.controller.evalutor.request.EvaluatorRequestTo;
import de.hsrw.dimitriosbarkas.ute.services.EvaluatorService;
import de.hsrw.dimitriosbarkas.ute.services.SafeExecuteTestService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotConvertToFileException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.CannotLoadConfigException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.TaskNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Base64;

@Log4j2
@RestController
public class EvaluatorController {

    @Autowired
    private EvaluatorService evaluatorService;

    @PostMapping(value = "/api/evaluate")
    public ResponseEntity<?> evaluateTest(@RequestBody EvaluatorRequestTo evaluatorRequestTo) throws CannotConvertToFileException, TaskNotFoundException, CannotLoadConfigException, IOException, XMLStreamException, InterruptedException {
        //TODO: Implement
//        byte[] decodedBytes = Base64.getDecoder().decode(evaluatorRequestTo.getEncodedTestContent());
//        log.info(new String(decodedBytes));
//        safeExecuteTestService.safelyExecuteTestInTempFolder("", evaluatorRequestTo.getEncodedTestContent());
        evaluatorService.evaluateTest(evaluatorRequestTo.getTaskId(), evaluatorRequestTo.getEncodedTestContent());
        return null;
    }
}
