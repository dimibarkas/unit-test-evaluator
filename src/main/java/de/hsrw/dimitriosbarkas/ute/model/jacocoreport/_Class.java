package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class _Class {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Method> method;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Counter> counter;

    private String name;

    private String sourcefilename;
}
