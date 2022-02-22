package de.hsrw.dimitriosbarkas.ute.model.surefirereport;

import lombok.Data;

@Data
public class Failure {
    public String message;
    public String type;
    public String text;
}
