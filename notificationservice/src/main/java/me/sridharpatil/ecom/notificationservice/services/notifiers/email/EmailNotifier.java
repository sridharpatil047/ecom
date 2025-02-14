package me.sridharpatil.ecom.notificationservice.services.notifiers.email;


import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.dtos.EmailRecipient;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.properties.EmailProperties;
import me.sridharpatil.ecom.notificationservice.models.EventType;
import me.sridharpatil.ecom.notificationservice.services.notifiers.*;
import me.sridharpatil.ecom.notificationservice.services.notifiers.templates.EventBasedTemplate;
import me.sridharpatil.ecom.notificationservice.services.notifiers.templates.EventBasedTemplateFactory;
import me.sridharpatil.ecom.notificationservice.utils.EmailUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

@Log4j2
@Service("email")
public class EmailNotifier implements Notifier {
    EmailProperties emailProperties;
    EventBasedTemplateFactory eventBasedTemplateFactory;

    public EmailNotifier(EmailProperties emailProperties, EventBasedTemplateFactory eventBasedTemplateFactory) {
        this.emailProperties = emailProperties;
        this.eventBasedTemplateFactory = eventBasedTemplateFactory;
    }

    @Override
    public void send(Recipient recipient, EventType eventType, Map<String, String> variables) throws MessagingException, UnsupportedEncodingException {
        log.debug("Email notification service called");

        EmailRecipient emailRecipient;
        if (recipient instanceof EmailRecipient) {
            emailRecipient = (EmailRecipient) recipient;
            log.debug("Recipient email: " + emailRecipient.getEmail());
        } else {
            log.error("Invalid recipient type");
            return;
        }

        EventBasedTemplate emailTemplate = eventBasedTemplateFactory.getTemplate(eventType, variables);

        log.debug("Creating SMTP session");
        Session session = createSmtpSession();

        log.info("Sending email to: " + emailRecipient.getEmail());
        EmailUtil.sendEmail(
                session,
                emailProperties.getUsername(),
                emailProperties.getEmailId(), // from email
                emailRecipient.getEmail(), // to email
                emailTemplate.getSubject(),
                emailTemplate.getBody()
        );
        log.info("Email sent successfully!!");
    }

    private Session createSmtpSession() {
        log.debug("Configuring email properties");
        Properties props = new Properties();
        props.put("mail.smtp.host", emailProperties.getHost()); //SMTP Host
        props.put("mail.smtp.port", emailProperties.getPort()); //TLS Port
        props.put("mail.smtp.auth", emailProperties.getEnableAuth()); //enable authentication
        props.put("mail.smtp.starttls.enable", emailProperties.getEnableTls()); //enable STARTTLS

        log.debug("Creating Authenticator object for "+ emailProperties.getEmailId());
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getEmailId(), emailProperties.getPassword());
            }
        };

        log.debug("Creating session object");
        return Session.getInstance(props, auth);
    }
}
