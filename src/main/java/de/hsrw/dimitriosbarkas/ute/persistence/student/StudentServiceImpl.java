package de.hsrw.dimitriosbarkas.ute.persistence.student;

import lombok.extern.log4j.Log4j2;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
}
