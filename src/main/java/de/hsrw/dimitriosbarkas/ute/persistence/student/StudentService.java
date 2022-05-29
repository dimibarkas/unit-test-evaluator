package de.hsrw.dimitriosbarkas.ute.persistence.student;

public interface StudentService {

    Student registerStudent(Long id, String email);

    Student getStudentById(Long id);

}
