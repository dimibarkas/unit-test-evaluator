package de.hsrw.dimitriosbarkas.ute.model;

import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResult {

    private String processOutput;

    private int processExitValue;

    private Report report;

    private BuildSummary summary;
}
