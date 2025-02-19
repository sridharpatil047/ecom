package me.sridharpatil.ecom.userservice.services.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService {

    ObjectMapper objectMapper;
    KafkaTemplate<String, String> kafkaTemplate;

    public NotificationServiceImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Notification notification) throws JsonProcessingException {

        log.debug("Sending notification: to - {} , message - {}", notification.getTo(), notification.getMessage());

        objectMapper.writeValueAsString(notification.getMessage());
        kafkaTemplate.send(notification.getTo(),
                objectMapper.writeValueAsString(notification.getMessage()));

        log.debug("Notification sent");
    }
}
