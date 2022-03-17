package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.model.Hint;
import de.hsrw.dimitriosbarkas.ute.model.SubmissionResult;
import de.hsrw.dimitriosbarkas.ute.model.Task;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Line;
import de.hsrw.dimitriosbarkas.ute.model.jacocoreport.Sourcefile;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import de.hsrw.dimitriosbarkas.ute.persistence.user.User;
import de.hsrw.dimitriosbarkas.ute.services.FeedbackService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FeedbackServiceImpl implements FeedbackService {


    //TODO: Suche im Report nach der richtigen Datei und geben die Liste der Lines aus.
    @Override
    public String provideFeedback(User user, Task task, SubmissionResult submissionResult) {

        // check the submission done by this user.
        List<Submission> submissionList = user.getSubmissionList().stream().sorted().collect(Collectors.toList());

        // if the current build was not successful, provide more general feedback
        if (submissionResult.getSummary() != BuildSummary.BUILD_SUCCESSFUL) {
            return "ich gebe dir einen generellen Tipp, wie versuch doch erstmal eine Instanz der Klasse zu bilden";
        }

        // if the current build was successful, provide feedback based on the last report and based on the lines with missed instructions/branches
        List<Line> lineList = submissionResult.getReport()._package.sourcefile.stream().filter(sourcefile -> Objects.equals(sourcefile.getName(), task.getSourcefilename())).collect(Collectors.toList()).stream().findFirst().orElseThrow(NullPointerException::new).getLine();

        log.info(getFeedback(task, lineList));

        submissionList.forEach(System.out::println);
        lineList.forEach(System.out::println);

        return null;
    }

    String getFeedback(Task task, List<Line> lineList) {
        List<Hint> hintList = task.getHintList();
        for (Line line : lineList) {
            Optional<Hint> optionalHint = hintList.stream().filter(_hint -> _hint.getNr() == line.nr).findFirst();
            if (optionalHint.isEmpty()) {
                return null;
            }
            Hint hint = optionalHint.get();
//            log.info(String.format("found hint for line %d", hint.getNr()));
            if (line.mi > 0 && hint.getIsMissedInstruction() != null) {
                return getMissedInstructionHintForLine(hint.getIsMissedInstruction());
            }
            if (line.mb > 0 && hint.getIsMissedBranch() != null) {
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
