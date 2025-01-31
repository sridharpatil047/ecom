package me.sridharpatil.ecom.notificationservice.consumers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.consumers.dtos.ConsumeOrderCancelledEventDto;
import me.sridharpatil.ecom.notificationservice.consumers.dtos.SendEmailNotificationMessage;
import me.sridharpatil.ecom.notificationservice.consumers.dtos.SendSmsNotificationMessage;
import me.sridharpatil.ecom.notificationservice.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Log4j2
@Service
public class EventConsumer {
    ObjectMapper objectMapper;
    NotificationService notificationService;

    public EventConsumer(ObjectMapper objectMapper, NotificationService notificationService) {
        this.objectMapper = objectMapper;
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "user-service.send-notification",
            groupId = "notification-service.consumers"
    )
    public void sendNotificationHandler(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Payload String message
    ) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {

        if (key.equals("email")){
            SendEmailNotificationMessage sendEmailNotificationMessage
                    = objectMapper.readValue(message, SendEmailNotificationMessage.class);
            notificationService.send(
                    sendEmailNotificationMessage.getRecipient(),
                    sendEmailNotificationMessage.getEventType()
            );
        } else if (key.equals("sms")){
            SendSmsNotificationMessage sendSmsNotificationMessage
                    = objectMapper.readValue(message, SendSmsNotificationMessage.class);

            notificationService.send(
                    sendSmsNotificationMessage.getRecipient(),
                    sendSmsNotificationMessage.getEventType()
            );
        } else {
            log.error("Invalid notification type");
        }
    }


    @KafkaListener(
            topics = "order-service.order-confirmed",
            groupId = "notification-service.consumers"
    )
    public void consumeOrderConfirmedEvent(@Payload String message){

        log.info("Order confirmed event received: " + message);



    }

    @KafkaListener(
            topics = "order-service.order-cancelled",
            groupId = "notification-service.consumers"
    )
    public void consumeOrderCancelledEvent(@Payload String message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {

        log.info("Order cancelled event received: " + message);

        ConsumeOrderCancelledEventDto consumeOrderCancelledEventDto = objectMapper.readValue(message, ConsumeOrderCancelledEventDto.class);

        notificationService.handleOrderCancelledEvent(
                consumeOrderCancelledEventDto.getUserId(),
                consumeOrderCancelledEventDto.getOrderId(),
                consumeOrderCancelledEventDto.getStatus()
        );

    }

}
