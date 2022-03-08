package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import lombok.Data;


/**
 *  This class represents a line in a sourcecode.
 *
 *  - When mb or cb is greater than 0 the line is a branch.
 *  - When mb and cb are 0 the line is a statement.
 *  - cb / (mb+cb) is 2/4 partial hit.
 *  - When not a branch and mi == 0 the line is hit (green)
 *
 *   more information: https://www.eclemma.org/jacoco/trunk/doc/counters.html
 */
@Data
public class Line {
    /**
     * Describes the line number.
     */
    public int nr;
    /**
     * Describes the missed instructions or statements per line.
     */
    public int mi;
    /**
     * Describes the covered instructions or statements per line.
     */
    public int ci;

    /**
     * Describes the missed branches per line.
     */
    public int mb;

    /**
     * Describes the covered branches per line.
     */
    public int cb;
}
