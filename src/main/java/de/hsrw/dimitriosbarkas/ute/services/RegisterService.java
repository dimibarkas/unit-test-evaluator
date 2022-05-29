package de.hsrw.dimitriosbarkas.ute.services;

import java.io.IOException;

public interface RegisterService {
    /**
     * Sends an e-mail to a student with link to unit-test-evaluator frontend site.
     * @param studentId id of student
     * @param studentEmailAddress email address of student
     * @throws IOException if template cannot be loaded
     */
    void registerForUnitTestEvaluator(Long studentId, String studentEmailAddress) throws IOException;
}
