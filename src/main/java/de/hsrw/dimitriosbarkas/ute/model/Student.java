package de.hsrw.dimitriosbarkas.ute.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        return result;
    }
}
