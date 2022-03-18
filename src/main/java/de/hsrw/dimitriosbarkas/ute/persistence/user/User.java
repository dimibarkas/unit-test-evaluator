package de.hsrw.dimitriosbarkas.ute.persistence.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class represents a user using the application.
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
    @JsonIgnore
    private Set<Submission> submissionList = new HashSet<>();

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void addSubmission(Submission submission) {
        this.submissionList.add(submission);
    }
}