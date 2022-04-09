package de.hsrw.dimitriosbarkas.ute.persistence.user;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * This method saves a user in the database.
     * @return the generated id
     */
    User createUser();

    /**
     * This method saves a new submission related to the user.
     * @param userId the id of the specified user.
     */
    void addSubmission(UUID userId, String taskId, int coveredInstructions, int coveredBranches, BuildSummary summary, boolean allMutationsPassed);

    /**
     * This method find a user by its id.
     * @param id the id of the given user
     */
    User getUserById(UUID id);

    /**
     * This method takes the UUID of the user and returns all submissions made by him.
     * @param id the specified UUID of the user
     * @return the List of all submissions
     */
    List<Submission> getSubmissionsOfUser(UUID id);
}
