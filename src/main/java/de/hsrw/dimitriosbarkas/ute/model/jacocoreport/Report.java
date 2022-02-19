package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class Report {

    public Sessioninfo sessioninfo;

    @JsonProperty("package")
    public _Package _package;

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Counter> counter;

    public String name;
}
