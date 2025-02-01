package me.sridharpatil.ecom.orderservice.services.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.orderservice.models.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
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

    public void orderConfirmedEvent(Order order) throws JsonProcessingException {
        log.debug("Entered orderConfirmedEvent");

        OrderConfirmationReqDto orderConfirmationReqDto = OrderConfirmationReqDto.of(order);
        String message = objectMapper.writeValueAsString(orderConfirmationReqDto);
        kafkaTemplate.send(
                "order-service.order-confirmed",
                orderConfirmationReqDto.getOrderId().toString(),
                message
        );
    }

    public void orderCancelledEvent(Order order) throws JsonProcessingException {
        OrderCancellationReqDto orderCancellationReqDto = OrderCancellationReqDto.of(order);
        String message = objectMapper.writeValueAsString(orderCancellationReqDto);
        kafkaTemplate.send(
                "order-service.order-cancelled",
                orderCancellationReqDto.getOrderId().toString(),
                message
        );
    }
}
