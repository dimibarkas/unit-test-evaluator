package de.hsrw.dimitriosbarkas.ute.model.pitest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mutation {

    private String sourceFile;

    private String mutatedClass;

    private String mutatedMethod;

    private String methodDescription;

    private int lineNumber;

    private String mutator;

    private int index;

    private int block;

    private String killingTest;

    private String description;

    private boolean detected;

    private String status;

    private int numberOfTestsRun;

    private String text;
}
