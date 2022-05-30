package de.hsrw.dimitriosbarkas.ute.persistence.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Set<Submission> submissionList = new HashSet<>();

    private String authKey;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void addSubmission(Submission submission) {
        this.submissionList.add(submission);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int salt;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            salt = sr.nextInt();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = result * salt;
        return result;
    }
}
