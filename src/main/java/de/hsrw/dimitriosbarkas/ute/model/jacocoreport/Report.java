package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Report {

    public Sessioninfo sessioninfo;

    @JsonProperty("package")
    public _Package _package;

    public List<Counter> counter;

    public String name;
}
