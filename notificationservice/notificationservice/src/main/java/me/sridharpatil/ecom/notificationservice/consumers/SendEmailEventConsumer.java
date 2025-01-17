package me.sridharpatil.ecom.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.consumers.dtos.SendEmailDto;
import me.sridharpatil.ecom.notificationservice.properties.EmailProperties;
import me.sridharpatil.ecom.notificationservice.utils.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Log4j2
@Service
public class SendEmailEventConsumer {

    ObjectMapper objectMapper;
    EmailProperties emailProperties;

    public SendEmailEventConsumer(ObjectMapper objectMapper, EmailProperties emailProperties) {
        this.objectMapper = objectMapper;
        this.emailProperties = emailProperties;
    }

    @KafkaListener(
            topics = "user-service.send-email",
            groupId = "notification-service.email-sender"
    )
    public void sendEmailHandler(String message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {

        log.debug("Received message: " + message);

        // Convert message to SendEmailDto
        SendEmailDto emailDto = objectMapper.readValue(message, SendEmailDto.class);

        // Create properties for email
        log.debug("Configuring email properties");
        Properties props = new Properties();
        props.put("mail.smtp.host", emailProperties.getHost()); //SMTP Host
        props.put("mail.smtp.port", emailProperties.getPort()); //TLS Port
        props.put("mail.smtp.auth", emailProperties.getEnableAuth()); //enable authentication
        props.put("mail.smtp.starttls.enable", emailProperties.getEnableTls()); //enable STARTTLS

        log.debug("Creating Authenticator object for "+ emailProperties.getEmailId());
        // Create Authenticator object to pass in Session.getInstance
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getEmailId(), emailProperties.getPassword());
            }
        };

        // Create session
        log.debug("Creating session object");
        Session session = Session.getInstance(props, auth);

        // Send email
        log.info("Sending email to: " + emailDto.getTo());
        EmailUtil.sendEmail(
                session,
                emailProperties.getUsername(),
                emailProperties.getEmailId(), // from email
                emailDto.getTo(),
                emailDto.getSubject(),
                emailDto.getBody()
        );
        log.info("Email sent successfully!!");
    }
}
