package de.hsrw.dimitriosbarkas.ute.model;

import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the returned object from the backend after a task was submitted by a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResult {
    /**
     * The build output of the console in the safe directory.
     */
    private String output;
    /**
     * The generated jacoco report mapped into a report object.
     */
    private Report report;
    /**
     * A build summary which determines if the last build was successfully, failed or if the build was successfully but the tests failed.
     */
    private BuildSummary summary;
}
