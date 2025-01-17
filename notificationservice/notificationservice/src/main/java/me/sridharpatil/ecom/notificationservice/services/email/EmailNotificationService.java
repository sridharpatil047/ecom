package me.sridharpatil.ecom.notificationservice.services.email;


import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.dtos.EmailRecipient;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.dtos.SendEmailNotificationDto;
import me.sridharpatil.ecom.notificationservice.properties.EmailProperties;
import me.sridharpatil.ecom.notificationservice.services.Event;
import me.sridharpatil.ecom.notificationservice.services.NotificationService;
import me.sridharpatil.ecom.notificationservice.utils.EmailUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Log4j2
@Service
public class EmailNotificationService implements NotificationService {
    EmailProperties emailProperties;

    public EmailNotificationService(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Recipient recipient, Event event) throws MessagingException, UnsupportedEncodingException {
        log.debug("Email notification service called");

        log.debug("Checking if recipient is of type EmailRecipient");
        if (!(recipient instanceof EmailRecipient)) {
            log.error("Recipient is not of type EmailRecipient");
            return;
        }
        EmailRecipient emailRecipient = (EmailRecipient) recipient;

        log.debug("Creating SMTP session");
        Session session = createSmtpSession();

        log.debug("Creating email template and preparing email");
        EmailTemplate emailTemplate = new EmailTemplate(emailRecipient, event);
        SendEmailNotificationDto emailNotificationDto = emailTemplate.prepareEmail();

        log.info("Sending email to: " + emailNotificationDto.getTo());
        EmailUtil.sendEmail(
                session,
                emailProperties.getUsername(),
                emailProperties.getEmailId(), // from email
                emailNotificationDto.getTo(),
                emailNotificationDto.getSubject(),
                emailNotificationDto.getMessage()
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
