package de.hsrw.dimitriosbarkas.ute.persistence.submission;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * This class represents the progress in a specific task of a user.
 * <p>
 * It needs to save the submission results in order to keep track of the progress and the number of tries he needs.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "submissions")
@ToString
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskId;

    private int percentage;

    @Column(name= "user_id")
    private UUID userid;

}
