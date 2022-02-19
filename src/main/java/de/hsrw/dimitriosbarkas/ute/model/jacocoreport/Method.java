package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class Method {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("counter")
    public List<Counter> counter;

    public String name;

    public String desc;

    public int line;
}