package de.hsrw.dimitriosbarkas.ute.model;

import lombok.Data;

import java.util.List;

@Data
public class Hint {
    private int nr;

    private List<String> isMissedInstruction;

    private List<String> isMissedBranch;
}
