package de.hsrw.dimitriosbarkas.ute.persistence.user;

import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.SubmissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final SubmissionRepository submissionRepository;

    public UserServiceImpl(UserRepository userRepository, SubmissionRepository submissionRepository) {
        this.userRepository = userRepository;
        this.submissionRepository = submissionRepository;
    }

    @Override
    public User createUser() {
        User user = new User();
        userRepository.save(user);
        //log.debug(user);
        return user;
    }

    @Override
    public void addSubmission(UUID userId, String taskId, int coveredInstructions, int coveredBranches) {
        User user = userRepository.getById(userId);
        //log.debug(user);
        Submission submission = new Submission();
        submission.setTaskId(taskId);
        submission.setUserid(user.getId());
        submission.setCoveredInstructions(coveredInstructions);
        submission.setCoveredBranches(coveredBranches);
        Submission savedSubmission = submissionRepository.save(submission);
        user.addSubmission(savedSubmission);
        userRepository.save(user);
    }
}
