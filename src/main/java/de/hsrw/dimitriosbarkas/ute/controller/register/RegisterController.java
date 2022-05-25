package de.hsrw.dimitriosbarkas.ute.controller.register;

import de.hsrw.dimitriosbarkas.ute.services.RegisterService;
import de.hsrw.dimitriosbarkas.ute.utils.ErrorResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(value = "/api/{studentId}/register")
    public ResponseEntity<?> registerForUnitTestEvaluator(@PathVariable("studentId") Long studentNumber,
                                                          @RequestParam String firstname,
                                                          @RequestParam String lastname,
                                                          @RequestParam String email) {
        if(email == null || firstname == null || lastname == null) {
            return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, "Die Felder Vorname, Nachname und E-Mail sind Pflichtfelder.");
        }

        try {
            registerService.registerForUnitTestEvaluator(studentNumber, firstname, lastname, email);
        } catch (IOException e) {
            log.error("Registration (failed): Cannot load configuration file.");
            return ErrorResponseUtil.buildTechnicalServerError("Konfigurationsdatei konnte nicht geladen werden.");
        }

        log.info("Registration (ok): Student ID " + studentNumber + " with e-mail address "  + email + ".");
        return ResponseEntity.ok().build();
    }


}
