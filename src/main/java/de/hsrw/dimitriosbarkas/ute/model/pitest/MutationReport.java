package de.hsrw.dimitriosbarkas.ute.model.pitest;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class MutationReport {

    @JacksonXmlProperty(localName = "mutation")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Mutation> mutations;
}
