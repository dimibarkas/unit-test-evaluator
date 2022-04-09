package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import lombok.Data;

@Data
public class Counter {

    private String type;

    private int missed;

    private int covered;
}
