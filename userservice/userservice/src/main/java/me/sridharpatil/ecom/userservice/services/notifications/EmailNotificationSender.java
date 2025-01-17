package me.sridharpatil.ecom.userservice.services.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.Event;
import me.sridharpatil.ecom.userservice.services.dtos.EmailRecipient;
import me.sridharpatil.ecom.userservice.services.dtos.SendEmailNotificationMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationSender implements NotificationSender {

    ObjectMapper objectMapper;
    KafkaTemplate<String, String> kafkaTemplate;

    public EmailNotificationSender(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendNotification(User user, Event event) throws JsonProcessingException {
        SendEmailNotificationMessage sendEmailNotificationMessage
                = new SendEmailNotificationMessage(
                        new EmailRecipient(user.getEmail(), user.getName()),
                        Event.USER_CREATED
        );

        String message = objectMapper.writeValueAsString(sendEmailNotificationMessage);
        kafkaTemplate.send("user-service.send-notification", "email", message);
    }
}
