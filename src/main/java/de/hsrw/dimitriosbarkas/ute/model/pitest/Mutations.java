package de.hsrw.dimitriosbarkas.ute.model.pitest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Mutations {

    private List<Mutation> mutations;
}
