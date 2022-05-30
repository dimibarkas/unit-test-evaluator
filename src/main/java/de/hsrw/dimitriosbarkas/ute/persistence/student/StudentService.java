package de.hsrw.dimitriosbarkas.ute.persistence.student;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;

import java.util.List;

public interface StudentService {
    /**
     * This method takes the studentId and the email of a student to create an entity in the database.
     * @param id id of student
     * @param email email of student
     * @return the new created student entity
     */
    Student registerStudent(Long id, String email);

    /**
     * This method finds a student in the database by his student id.
     * @param id if of student
     * @return student object
     */
    Student getStudentById(Long id);

    /**
     * This method saves a new submission related to the user.
     * @param studentId the id of the specified student.
     */
    void addSubmission(Long studentId, String taskId, int coveredInstructions, int coveredBranches, BuildSummary summary, boolean allMutationsPassed);

    /**
     * This method takes the UUID of the user and returns all submissions made by him.
     * @param studentId the specified UUID of the user
     * @return the List of all submissions
     */
    List<Submission> listSubmissionsOfStudent(Long studentId);

}
