package de.hsrw.dimitriosbarkas.ute.services;

import java.io.IOException;

public interface RegisterService {
    /**
     * Sends an e-mail to a student with link to unit-test-evaluator frontend site.
     * @param studentNumber student number
     * @param studentEmailAddress email address of student
     * @throws IOException if template cannot be loaded
     */
    void registerForUnitTestEvaluator(Long studentNumber,String studentFirstname, String studentLastname, String studentEmailAddress) throws IOException;
}
