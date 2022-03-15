package de.hsrw.dimitriosbarkas.ute.model;

import lombok.Data;

import java.util.List;

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
     * The short description of the task which should be displayed in the selection.
     */
    private String shortDescription;
    /**
     * The description of the target.
     */
    private String targetDescription;
    /**
     * The path to the directory with the files needed for the source files.
     */
    private String pathToDir;
    /**
     * The name of the file which should get tested.
     */
    private String sourcefilename;
    /**
     * The file content base-64 encoded.
     */
    private String encodedFile;
    /**
     * The path to the file with the test-template.
     */
    private String testtemplatefilename;
    /**
     * The test template file content base-64 encoded.
     */
    private String encodedTestTemplate;
    /**
     * A list of possible hints.
     */
    private List<Hint> hintList;
}
