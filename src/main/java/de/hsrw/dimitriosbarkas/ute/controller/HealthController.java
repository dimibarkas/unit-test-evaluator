package de.hsrw.dimitriosbarkas.ute.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class HealthController {

    @GetMapping(value = "/api/health")
    public String sendOK() {
        return "[Unit-Test-Evaluator]: OK";
    }


}
