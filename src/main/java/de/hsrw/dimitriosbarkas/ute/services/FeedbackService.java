package de.hsrw.dimitriosbarkas.ute.services;

import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.persistence.student.Student;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.NoFeedbackFoundException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.NoHintProvidedException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.SourcefileNotFoundException;

/**
 * This interface provides methods to provide feedback based on the submission of a user.
 */
public interface FeedbackService {

    /**
     * This function reads the coverage report in a submission, the progress of a user and the task he is currently working on and
     * decided which Feedback to provide.
     * @param student the student who gets the feedback
     * @param task the task the user is currently working on (providing the hints)
     * @param submissionResult the submission result keeps the coverage report which
     * @return the title of the feedback video
     *
     */
    String provideFeedback(Student student, Task task, SubmissionResult submissionResult) throws SourcefileNotFoundException, NoHintProvidedException, NoFeedbackFoundException;
}
