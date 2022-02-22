package de.hsrw.dimitriosbarkas.ute.model.surefirereport;

import lombok.Data;

@Data
public class Testcase {
    public Failure failure;
    public String classname;
    public String name;
    public double time;
    public String text;
}
