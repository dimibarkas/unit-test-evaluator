package de.hsrw.dimitriosbarkas.ute.model.pitest;

import lombok.Data;

@Data
public class Mutation {
    private String sourceFile;
    private String mutatedClass;
    private String mutatedMethod;
    private String methodDescription;
    private int lineNumber;
    private String mutator;
    private int index;
    private int block;
    private Object killingTest;
    private String description;
    private boolean detected;
    private String status;
    private int numberOfTestsRun;
}
