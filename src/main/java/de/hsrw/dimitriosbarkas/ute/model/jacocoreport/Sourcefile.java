package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import lombok.Data;

import java.util.List;

@Data
public class Sourcefile {

    public List<Line> line;

    public List<Counter> counter;

    public String name;
}
