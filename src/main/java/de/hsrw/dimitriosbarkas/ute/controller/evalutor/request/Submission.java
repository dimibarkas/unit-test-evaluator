package de.hsrw.dimitriosbarkas.ute.controller.evalutor.request;

import lombok.Data;

/**
 * This class represents a submission on a task specified by the id.
 */
@Data
public class Submission {
    /**
     * The id for the specified task
     */
    private String taskId;
    /**
     * The base 64 encoded test file.
     */
    private String encodedTestContent;
}
