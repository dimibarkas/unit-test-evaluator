package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.persistence.user.User;

/**
 * This interface provides methods to provide feedback based on the submission of a user.
 */
public interface FeedbackService {

    /**
     * This function reads the coverage report in a submission, the progress of a user and the task he is currently working on and
     * decided which Feedback to provide.
     * @param user the user who gets the feedback
     * @param task the task the user is currently working on (providing the hints)
     * @param submissionResult the submission result keeps the coverage report which
     * @return the title of the feedback video
     *
     */
    String provideFeedback(User user, Task task, SubmissionResult submissionResult);
}
