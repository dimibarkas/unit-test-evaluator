package de.hsrw.dimitriosbarkas.ute.services;

import java.util.Map;

public interface EmailService {
    /**
     * Send e-mail from default e-mail account.
     * @param eMailTo recipient's e-mail address
     * @param subject subject
     * @param body body
     */
    void sendEmail(String eMailTo, String subject, String body);

    /**
     * send e-mail from default e-mail account using a template.
     * @param eMailTo recipient's e-mail address
     * @param subject subject
     * @param bodyTemplate template for body
     * @param templateValues values to fill body with
     */
    void sendEmailWithTemplate(String eMailTo, String subject, String bodyTemplate, Map<String, String> templateValues);
}
