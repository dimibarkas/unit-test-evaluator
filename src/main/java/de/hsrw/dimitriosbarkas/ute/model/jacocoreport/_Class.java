package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class _Class {

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Method> method;

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Counter> counter;

    public String name;

    public String sourcefilename;
}
