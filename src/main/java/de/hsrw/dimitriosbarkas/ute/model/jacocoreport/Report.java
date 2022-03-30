package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class Report {

    private Sessioninfo sessioninfo;

    @JacksonXmlProperty(localName = "package")
    private  _Package _package;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Counter> counter;

    private String name;
}
