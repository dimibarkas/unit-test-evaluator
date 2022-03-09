package de.hsrw.dimitriosbarkas.ute.persistence.user;

import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a user who is using the application.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appuser")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Submission> submissionList = new ArrayList<>();

    public void addSubmission(Submission submission) {
        this.submissionList.add(submission);
    }
}