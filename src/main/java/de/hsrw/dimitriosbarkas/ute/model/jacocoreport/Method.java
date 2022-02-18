package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import lombok.Data;

import java.util.List;

@Data
public class Method {

    public List<Counter> counter;

    public String name;

    public String desc;

    public int line;
}