package de.hsrw.dimitriosbarkas.ute.controller.evalutor.request;

import lombok.Data;

@Data
public class EvaluatorRequestTo {
    private String taskId;

    private String encodedTestContent;
}
