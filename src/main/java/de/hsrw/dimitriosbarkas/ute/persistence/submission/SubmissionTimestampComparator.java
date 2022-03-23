package de.hsrw.dimitriosbarkas.ute.persistence.submission;

import java.util.Comparator;

public class SubmissionTimestampComparator implements Comparator<Submission> {

    @Override
    public int compare(Submission o1, Submission o2) {
        return o1.getSubmittedAt().compareTo(o2.getSubmittedAt());
    }
}
