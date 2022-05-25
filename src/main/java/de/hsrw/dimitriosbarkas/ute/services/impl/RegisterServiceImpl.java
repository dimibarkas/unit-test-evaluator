package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.model.Student;
import de.hsrw.dimitriosbarkas.ute.services.EmailService;
import de.hsrw.dimitriosbarkas.ute.services.RegisterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Value("classpath:emails/register-for-post-exam-review-mail.txt")
    private Resource emailTemplateResource;

    @Value("${exam-review.frontend-base-url}")
    private String frontendBaseUrl;

    private final EmailService emailService;

    public RegisterServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void registerForUnitTestEvaluator(Long studentNumber,String studentFirstname, String studentLastname, String studentEmailAddress) throws IOException {

        Student student = Student.builder().id(studentNumber).firstname(studentFirstname).lastname(studentLastname).email(studentEmailAddress).build();

        // Read e-mail template
        String bodyTemplate;
        try (Reader reader = new InputStreamReader(emailTemplateResource.getInputStream(), StandardCharsets.UTF_8)) {
            bodyTemplate = FileCopyUtils.copyToString(reader);
        }

        // Send email
        Map<String, String> values = new HashMap<>();
        values.put("url", frontendBaseUrl + "?studentNumber=" + student.getId() + "&authKey=" + student.hashCode());
        emailService.sendEmailWithTemplate(studentEmailAddress, "Link zum Tool", bodyTemplate, values);
    }
}
