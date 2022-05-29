package de.hsrw.dimitriosbarkas.ute.persistence.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "student")
@Getter
@Setter
@Log4j2
public class Student {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String authKey;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public int hashCode() {
        int prime;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            prime = sr.nextInt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        return result;
    }
}
