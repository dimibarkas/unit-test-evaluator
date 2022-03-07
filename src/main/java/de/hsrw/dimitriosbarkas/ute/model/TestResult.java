package de.hsrw.dimitriosbarkas.ute.model;

import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {

    private String output;

    private Report report;

    private BuildSummary summary;
}
