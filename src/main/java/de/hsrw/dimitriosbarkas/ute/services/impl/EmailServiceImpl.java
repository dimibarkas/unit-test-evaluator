package de.hsrw.dimitriosbarkas.ute.services.impl;

import de.hsrw.dimitriosbarkas.ute.services.EmailService;
import lombok.extern.log4j.Log4j2;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {

    @Value("${messaging.send-emails}")
    private Boolean sendEmails;

    @Value("${messaging.e-mail.from}")
    private String messagingEmailFrom;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String eMailTo, String subject, String body) {
        if(sendEmails) {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom(messagingEmailFrom);
                helper.setTo(eMailTo);
                helper.setSubject(subject);
                helper.setText(body, true);
            };

            javaMailSender.send(messagePreparator);
        } else {
            // Debug only
            log.info("DEBUG: Now e-mail would be sent from " + messagingEmailFrom + " to " + eMailTo + " with subject: " + subject);
            log.info(body);
        }
    }

    @Override
    public void sendEmailWithTemplate(String eMailTo, String subject, String bodyTemplate, Map<String, String> templateValues) {
        // evaluate context
        StringWriter bodyWriter = new StringWriter();
        VelocityContext velocityContext = new VelocityContext();
        for(String value: templateValues.keySet()) {
            velocityContext.put(value, templateValues.get(value));
        }
        Velocity.evaluate(velocityContext, bodyWriter, "sendEmailWithTemplate", bodyTemplate);

        //send e-mail
        sendEmail(eMailTo, subject, bodyWriter.toString());
    }
}
