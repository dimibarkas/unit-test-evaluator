package de.hsrw.dimitriosbarkas.ute.model;

import lombok.Data;

/**
 * This class represents a task for a user to work on.
 */
@Data
public class Task {

    /**
     * The id of the task.
     */
    private String id;
    /**
     * The title of the task.
     */
    private String name;
    /**
     * The description of the task.
     */
    private String description;
    /**
     * The description of the target.
     */
    private String targetDescription;
    /**
     * The path to the file.
     */
    private String pathToFile;
    /**
     * The file content base-64 encoded.
     */
    private String encodedFile;
    /**
     * The path to the file with the test-template.
     */
    private String pathToTestTemplate;
    /**
     * The test template file content base-64 encoded.
     */
    private String encodedTestTemplate;
}
