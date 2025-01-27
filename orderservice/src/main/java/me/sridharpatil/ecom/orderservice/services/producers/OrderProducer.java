package me.sridharpatil.ecom.orderservice.services.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.orderservice.models.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void orderCreatedEvent(Order order) throws JsonProcessingException {
        OrderCreatedEventDto orderCreatedEventDto = OrderCreatedEventDto.of(order);
        String message = objectMapper.writeValueAsString(orderCreatedEventDto);
        kafkaTemplate.send(
                "order-service.order-created",
                orderCreatedEventDto.getOrderId().toString(),
                message
        );
    }
}
