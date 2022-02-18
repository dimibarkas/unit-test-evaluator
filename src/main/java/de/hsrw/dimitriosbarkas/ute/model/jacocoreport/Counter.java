package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import lombok.Data;

@Data
public class Counter {

    public String type;

    public int missed;

    public int covered;
}
