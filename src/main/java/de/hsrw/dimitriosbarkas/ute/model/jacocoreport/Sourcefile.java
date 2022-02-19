package de.hsrw.dimitriosbarkas.ute.model.jacocoreport;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

@Data
public class Sourcefile {

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Line> line;

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Counter> counter;

    public String name;
}
