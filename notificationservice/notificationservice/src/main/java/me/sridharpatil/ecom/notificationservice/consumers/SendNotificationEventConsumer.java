package me.sridharpatil.ecom.notificationservice.consumers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.consumers.dtos.SendEmailNotificationMessage;
import me.sridharpatil.ecom.notificationservice.consumers.dtos.SendSmsNotificationMessage;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.services.Event;
import me.sridharpatil.ecom.notificationservice.services.NotificationServiceContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Log4j2
@Service
public class SendNotificationEventConsumer {
    ObjectMapper objectMapper;
    NotificationServiceContext notificationServiceContext;

    public SendNotificationEventConsumer(ObjectMapper objectMapper, NotificationServiceContext notificationServiceContext) {
        this.objectMapper = objectMapper;
        this.notificationServiceContext = notificationServiceContext;
    }

    @KafkaListener(
            topics = "user-service.send-notification",
            groupId = "notification-service.notification-sender"
    )
    public void sendNotificationHandler(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Payload String message
    ) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {

        if (key.equals("email")){
            SendEmailNotificationMessage sendEmailNotificationMessage
                    = objectMapper.readValue(message, SendEmailNotificationMessage.class);
            notificationServiceContext.send(
                    sendEmailNotificationMessage.getRecipient(),
                    sendEmailNotificationMessage.getEvent()
            );
        } else if (key.equals("sms")){
            SendSmsNotificationMessage sendSmsNotificationMessage
                    = objectMapper.readValue(message, SendSmsNotificationMessage.class);

            notificationServiceContext.send(
                    sendSmsNotificationMessage.getRecipient(),
                    sendSmsNotificationMessage.getEvent()
            );
        } else {
            log.error("Invalid notification type");
        }
    }
}
