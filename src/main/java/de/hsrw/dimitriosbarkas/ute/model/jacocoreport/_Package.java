package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class _Package {

    @JacksonXmlProperty(localName = "class")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<_Class> _class;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Sourcefile> sourcefile;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Counter> counter;

    private String name;
}
