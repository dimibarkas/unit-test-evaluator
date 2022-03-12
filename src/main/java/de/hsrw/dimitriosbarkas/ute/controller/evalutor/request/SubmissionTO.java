package de.hsrw.dimitriosbarkas.ute.controller.evalutor.request;

import lombok.Data;

import java.util.UUID;

/**
 * This class represents a submission on a task specified by the id.
 */
@Data
public class SubmissionTO {
    /**
     * The id for the specified task
     */
    private String taskId;
    /**
     * The base 64 encoded test file.
     */
    private String encodedTestContent;
    /**
     * The id of the user.
     */
    private UUID userId;
}