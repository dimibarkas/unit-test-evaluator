package de.hsrw.dimitriosbarkas.ute.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Progress {
    private String id;
    private int coveredInstructions;
    private int coveredBranches;

}
