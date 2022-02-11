package de.hsrw.dimitriosbarkas.ute.model;

import lombok.Data;

@Data
public class Task {

    private String id;

    private String name;

    private String description;

    private String targetDescription;

    private String pathToFile;

    private String encodedFile;
}
