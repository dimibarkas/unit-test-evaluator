package de.hsrw.dimitriosbarkas.ute.persistence.submission;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class represents a submission which can be saved in the database.
 * <p>
 * We need to save the submission results in order to keep track of the progress and the number of tries he needs.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "submissions")
@ToString
public class Submission implements Comparable<Submission>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskId;

    @Column(name = "user_id")
    private UUID userid;

    @Column(name = "submitted_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime submittedAt;

    @Column(name = "covered_instructions")
    private int coveredInstructions;

    @Column(name = "covered_branches")
    private int coveredBranches;

    @Enumerated(EnumType.STRING)
    private BuildSummary summary;

    @Override
    public int compareTo(Submission o) {
        if(this.submittedAt.isEqual(o.submittedAt)) {
            return 0;
        } else if (this.submittedAt.isAfter(o.submittedAt)) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
