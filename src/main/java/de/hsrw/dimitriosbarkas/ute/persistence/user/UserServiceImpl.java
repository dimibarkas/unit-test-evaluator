package de.hsrw.dimitriosbarkas.ute.persistence.user;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.SubmissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

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
        return user;
    }

    @Override
    public void addSubmission(UUID userId, String taskId, int coveredInstructions, int coveredBranches, BuildSummary summary) {
        User user = userRepository.getById(userId);
        Submission submission = new Submission();
        submission.setTaskId(taskId);
        submission.setUserid(user.getId());
        submission.setCoveredInstructions(coveredInstructions);
        submission.setCoveredBranches(coveredBranches);
        submission.setSummary(summary);
        Submission savedSubmission = submissionRepository.save(submission);
        user.addSubmission(savedSubmission);
        userRepository.save(user);
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.getById(id);
    }

    @Override
    public List<Submission> getSubmissionsOfUser(UUID id) {
        return getUserById(id).getSubmissionList().stream().sorted().collect(Collectors.toList());
    }
}
