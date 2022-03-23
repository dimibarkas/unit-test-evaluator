package de.hsrw.dimitriosbarkas.ute.controller.user;

import de.hsrw.dimitriosbarkas.ute.model.Progress;
import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.SubmissionTimestampComparator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProgressTO {
    private List<Progress> progressList;

    public static ProgressTO fromConfig(TaskConfig taskConfig, List<Submission> submissionList) {
        //for each task in the config
        return new ProgressTO(taskConfig
                .getTasks()
                .stream()
                .map((task) -> {
            //search for the last submission done by the user (optional)
            Optional<Submission> lastSubmission = submissionList
                    .stream()
                    .filter(submission -> Objects.equals(submission.getTaskId(), task.getId()))
                    .max(Comparator.comparing(Submission::getSubmittedAt));

            return lastSubmission.map(
                    submission -> new Progress(task.getId(), submission.getCoveredInstructions(), submission.getCoveredBranches()))
                    .orElseGet(() -> new Progress(task.getId(), 0, 0));
        }).collect(Collectors.toList()));
    }
}
