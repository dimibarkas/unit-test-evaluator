package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.model.Hint;
import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Line;
import de.hsrw.dimitriosbarkas.ute.model.pitest.Mutation;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import de.hsrw.dimitriosbarkas.ute.persistence.user.User;
import de.hsrw.dimitriosbarkas.ute.services.FeedbackService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FeedbackServiceImpl implements FeedbackService {

    @Override
    public String provideFeedback(User user, Task task, SubmissionResult currentSubmissionResult) {

        // check the submissions done by this user.
        List<Submission> submissionList = user.getSubmissionList().stream().sorted().collect(Collectors.toList());

        // if the current build was not successful, provide more general feedback
        if (currentSubmissionResult.getSummary() != BuildSummary.BUILD_SUCCESSFUL) {
            log.info("Ein allgemeiner Tipp.");
        }

        //first try 100% lines and branches covered
        if (currentSubmissionResult.getSummary() == BuildSummary.BUILD_SUCCESSFUL
                && submissionList.get(0).getCoveredBranches() == 100
                && submissionList.get(0).getCoveredInstructions() == 100
        ) {
            log.info("Beim ersten versucht hast du sofort eine Line-Coverage von 100% erreicht? WOW!!!!");
        }

        // if the current build was successful, provide feedback based on the last report and based on the lines with missed instructions/branches
        List<Line> lineList = currentSubmissionResult
                .getReport()
                .get_package()
                .getSourcefile()
                .stream()
                .filter(sourcefile -> Objects.equals(sourcefile.getName(), task.getSourcefilename()))
                .collect(Collectors.toList()).stream().findFirst().orElseThrow(() -> new NullPointerException("sourcefile not found: ")).getLine();

        log.info("Mutation-Result: ");

        //this list need to be empty
        List<Mutation> mutationList = currentSubmissionResult
                .getMutationReport()
                .getMutations()
                .stream()
                .filter(mutation ->
                        mutation.getSourceFile().equals(task.getSourcefilename())
                        && !mutation.isDetected())
                .collect(Collectors.toList());

        if (mutationList.isEmpty()) {
            log.info("all mutations passed");
        } else {
            mutationList.forEach(System.out::println);
        }


//        submissionList.forEach(System.out::println);
//        lineList.forEach(System.out::println);

        return getFeedbackByLineCoverage(task, lineList);
    }

    String getFeedbackByLineCoverage(Task task, List<Line> lineList) {
        List<Hint> hintList = task.getHintList();
        for (Line line : lineList) {
            Optional<Hint> optionalHint = hintList.stream().filter(_hint -> _hint.getNr() == line.getNr()).findFirst();
            if (optionalHint.isEmpty()) {
                return null;
            }
            Hint hint = optionalHint.get();
//            log.info(String.format("found hint for line %d", hint.getNr()));
            if (line.getMi() > 0 && hint.getIsMissedInstruction() != null) {
                return getMissedInstructionHintForLine(hint.getIsMissedInstruction());
            }
            if (line.getMb() > 0 && hint.getIsMissedBranch() != null) {
                return getMissedBranchHintForLine(hint.getIsMissedBranch());
            }
        }
        return null;
    }

    String getMissedInstructionHintForLine(List<String> hintsForMissedInstructions) {
        return getRandomHintMessage(hintsForMissedInstructions);
    }

    String getMissedBranchHintForLine(List<String> hintsForMissedBranches) {
        return getRandomHintMessage(hintsForMissedBranches);
    }

    String getRandomHintMessage(List<String> hintMessages) {
        Random random = new Random();
        int randomItem = random.nextInt(hintMessages.size());
        return hintMessages.get(randomItem);
    }
}
