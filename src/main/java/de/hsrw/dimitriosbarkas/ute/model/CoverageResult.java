package de.hsrw.dimitriosbarkas.ute.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoverageResult {

    private int coveredInstructions;

    private int coveredBranches;
}
