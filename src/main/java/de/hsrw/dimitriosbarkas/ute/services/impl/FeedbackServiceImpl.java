package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.model.Hint;
import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Line;
import de.hsrw.dimitriosbarkas.ute.model.pitest.Mutation;
import de.hsrw.dimitriosbarkas.ute.persistence.student.Student;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import de.hsrw.dimitriosbarkas.ute.services.FeedbackService;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.NoFeedbackFoundException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.NoHintProvidedException;
import de.hsrw.dimitriosbarkas.ute.services.exceptions.SourcefileNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FeedbackServiceImpl implements FeedbackService {

    @Override
    public String provideFeedback(Student student, Task task, SubmissionResult currentSubmissionResult) throws SourcefileNotFoundException, NoFeedbackFoundException {

        // check the submissions done by this user.
        List<Submission> submissionList = student.getSubmissionList().stream().sorted().collect(Collectors.toList());


        // if the current build was not successful, provide more general feedback (optional)
        if (currentSubmissionResult.getSummary() != BuildSummary.BUILD_SUCCESSFUL) {
            return null;
        }

        //first try 100% lines and branches covered
        if (currentSubmissionResult.getSummary() == BuildSummary.BUILD_SUCCESSFUL && submissionList.get(0).getCoveredBranches() == 100 && submissionList.get(0).getCoveredInstructions() == 100) {
            log.info("First try. 100% Line-Coverage and Mutators passed.");
        }

        // if the current build was successful, provide feedback based on the last report and based on the lines with missed instructions/branches
        List<Line> lineList = currentSubmissionResult.getReport().get_package().getSourcefile().stream().filter(sourcefile -> sourcefile.getName().equals(task.getSourcefilename())).collect(Collectors.toList()).stream().findAny().orElseThrow(() -> new SourcefileNotFoundException("sourcefile not found:")).getLine();

        //this list need to be empty
        List<Mutation> mutationList = currentSubmissionResult.getMutationReport().getMutations().stream().filter(mutation -> mutation.getSourceFile().equals(task.getSourcefilename()) && !mutation.isDetected()).collect(Collectors.toList());


        if (mutationList.isEmpty()) {
            log.info("all mutations passed");
        } else {
            log.info("there are " + mutationList.size() + " missed mutations ");
        }

        if (submissionList.get(submissionList.size() - 1).getCoveredInstructions() == 100 && submissionList.get(submissionList.size() - 1).getCoveredBranches() == 100) {
            if (mutationList.isEmpty()) return null;
            return getRandomHintMessage(task.getMutatorHintList());
        } else {
            return getFeedbackByLineCoverage(task, lineList);
        }
    }

    String getFeedbackByLineCoverage(Task task, List<Line> lineList) throws  NoFeedbackFoundException {
//        log.info(lineList);
//        log.info(task.getHintList());
        List<Hint> hintList = task.getHintList();
        for (Line line : lineList) {
            log.info(line);
//            lineList.stream().filter(_line -> _line.getMi() > 0).findFirst().ifPresent(value -> log.info("Mi-Hint for line " + value.getNr() + " needed right here."));
//            lineList.stream().filter(_line -> _line.getMb() > 0).findFirst().ifPresent(value -> log.info("Mb-Hint for line " + value.getNr() + " needed right here."));

            //Do i need a hint for this line?


            Optional<Hint> optionalHint = hintList.stream().filter(_hint -> _hint.getNr() == line.getNr()).findFirst();
//                            .orElseThrow(() -> new NoHintProvidedException(line.getNr())));

//            Hint hint = optionalHint.get();
//            log.info(String.format("found hint for line %d", hint.getNr()));
            if (line.getMb() > 0 && optionalHint.isPresent()) {
                return getMissedBranchHintForLine(optionalHint.get().getIsMissedBranch());
            } else if (line.getMi() > 0 && optionalHint.isPresent()) {
                return getMissedInstructionHintForLine(optionalHint.get().getIsMissedInstruction());
            }

        }
        return null;
    }

    String getMissedInstructionHintForLine(List<String> hintsForMissedInstructions) throws NoFeedbackFoundException {
        return getRandomHintMessage(hintsForMissedInstructions);
    }

    String getMissedBranchHintForLine(List<String> hintsForMissedBranches) throws NoFeedbackFoundException {
        return getRandomHintMessage(hintsForMissedBranches);
    }

    String getRandomHintMessage(List<String> hintMessages) throws NoFeedbackFoundException {
        if (hintMessages.isEmpty()) {
            throw new NoFeedbackFoundException();
        }
        Random random = new Random();
        int randomItem = random.nextInt(hintMessages.size());
        return hintMessages.get(randomItem);
    }
}
