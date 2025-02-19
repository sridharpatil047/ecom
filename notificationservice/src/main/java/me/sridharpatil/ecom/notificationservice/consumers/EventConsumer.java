package me.sridharpatil.ecom.notificationservice.consumers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.consumers.dtos.*;
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

    @KafkaListener(topics = "user-service.user-created",
            groupId = "notification-service.consumers")
    public void consumeUserCreatedEvent(@Payload String message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {
        log.debug("Entered consumeUserCreatedEvent");

        Long userId = objectMapper.readValue(message, Long.class);
        notificationService.handleUserCreatedEvent(userId);

    }

    @KafkaListener(topics = "order-service.order-confirmed",
            groupId = "notification-service.consumers")
    public void consumeOrderConfirmedEvent(@Payload String message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {

        log.info("Order confirmed event received: " + message);

        ConsumeOrderConfirmedEventDto consumeOrderConfirmedEventDto = objectMapper.readValue(message, ConsumeOrderConfirmedEventDto.class);

        notificationService.handleOrderConfirmedEvent(
                consumeOrderConfirmedEventDto.getUserId(),
                consumeOrderConfirmedEventDto.getOrderId()
        );
    }

    @KafkaListener(topics = "order-service.order-cancelled",
            groupId = "notification-service.consumers")
    public void consumeOrderCancelledEvent(@Payload String message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {

        log.info("Order cancelled event received: " + message);

        ConsumeOrderCancelledEventDto consumeOrderCancelledEventDto = objectMapper.readValue(message, ConsumeOrderCancelledEventDto.class);

        notificationService.handleOrderCancelledEvent(
                consumeOrderCancelledEventDto.getUserId(),
                consumeOrderCancelledEventDto.getOrderId()
        );

    }

    @KafkaListener(topics = "user-service.password-resets",
            groupId = "notification-service.consumers")
    public void consumePasswordResetEvent(@Payload String message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {
        log.info("Password reset event received: {}", message);

        ConsumePasswordResetEventDto dto = objectMapper.readValue(message, ConsumePasswordResetEventDto.class);

        notificationService.handlePasswordResetEvent(dto.getUserId(),
                dto.getOtp());
    }
}
