package me.sridharpatil.ecom.notificationservice.services.email;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.dtos.EmailRecipient;
import me.sridharpatil.ecom.notificationservice.dtos.SendEmailNotificationDto;
import me.sridharpatil.ecom.notificationservice.services.Event;

@Log4j2
@Getter @Setter
public class EmailTemplate {
    EmailRecipient recipient;
    private Event event;

    public EmailTemplate(EmailRecipient recipient, Event event) {
        this.recipient = recipient;
        this.event = event;
    }

    public SendEmailNotificationDto prepareEmail() {
        SendEmailNotificationDto sendEmailNotificationDto = null;

        log.debug("Preparing email");
        if (this.event == Event.USER_CREATED) {
            log.trace("Preparing welcome email");
            sendEmailNotificationDto = new SendEmailNotificationDto(
                    this.recipient.getTo(),
                    "Welcome to Ecom, " + recipient.getName() + ". You have successfully signed up."
            );
            sendEmailNotificationDto.setSubject("Welcome to Ecom");
        }else if (this.event == Event.USER_UPDATED) {
            log.trace("Preparing profile updated email");
            sendEmailNotificationDto = new SendEmailNotificationDto(
                    this.recipient.getTo(),
                    "Hello, " + recipient.getName() + ". Your profile has been updated."
            );
            sendEmailNotificationDto.setSubject("Profile Updated");
        }
        return sendEmailNotificationDto;
    }
}
