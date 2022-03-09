package de.hsrw.dimitriosbarkas.ute.persistence.user;

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
    void addSubmission(UUID userId, String taskId, int coveragePercentage);
}
