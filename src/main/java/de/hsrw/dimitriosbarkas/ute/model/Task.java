package de.hsrw.dimitriosbarkas.ute.model;

import lombok.Data;

/**
 * This class represents a task for a user to work on.
 */
@Data
public class Task {

    private String id;

    private String name;

    private String description;

    private String targetDescription;

    private String pathToFile;

    private String encodedFile;

    private String pathToTestTemplate;

    private String encodedTestTemplate;
}
