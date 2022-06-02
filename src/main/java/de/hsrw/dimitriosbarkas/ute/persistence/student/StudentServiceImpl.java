package de.hsrw.dimitriosbarkas.ute.persistence.student;

import de.hsrw.dimitriosbarkas.ute.model.BuildSummary;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.Submission;
import de.hsrw.dimitriosbarkas.ute.persistence.submission.SubmissionRepository;
import lombok.extern.log4j.Log4j2;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final SubmissionRepository submissionRepository;

    public StudentServiceImpl(StudentRepository studentRepository, SubmissionRepository submissionRepository) {
        this.studentRepository = studentRepository;
        this.submissionRepository = submissionRepository;
    }


    @Override
    public Student registerStudent(Long id, String email) {
        String[] parts = email.split("\\.");
        String firstname = StringUtils.capitalise(parts[0]);
        String secondPart = parts[1];
        String lastname = StringUtils.capitalise(secondPart.substring(0, secondPart.indexOf("@")));
        Student student = new Student();
        student.setId(id);
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setEmail(email);
        student.setAuthKey(String.valueOf(student.hashCode()));
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.getById(id);
    }

    @Override
    public void addSubmission(Long studentId, String taskId, int coveredInstructions, int coveredBranches, BuildSummary summary, boolean allMutationsPassed) {
        Student student = getStudentById(studentId);
        Submission submission = new Submission();
        submission.setTaskId(taskId);
        submission.setStudentId(student.getId());
        submission.setCoveredInstructions(coveredInstructions);
        submission.setCoveredBranches(coveredBranches);
        submission.setSummary(summary);
        submission.setAllMutationsPassed(allMutationsPassed);
        Submission savedSubmission = submissionRepository.save(submission);
        student.addSubmission(savedSubmission);
        studentRepository.save(student);
    }

    @Override
    public List<Submission> listSubmissionsOfStudent(Long studentId) {
        return getStudentById(studentId).getSubmissionList().stream().sorted().collect(Collectors.toList());
    }

}
