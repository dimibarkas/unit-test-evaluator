package de.hsrw.dimitriosbarkas.ute.model.surefirereport;

import lombok.Data;

@Data
public class Testsuite {
    public Properties properties;
    public Testcase testcase;
    public int tests;
    public int failures;
    public String name;
    public double time;
    public int errors;
    public int skipped;
    public String text;
}
