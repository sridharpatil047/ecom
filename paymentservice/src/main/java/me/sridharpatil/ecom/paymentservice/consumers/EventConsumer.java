package me.sridharpatil.ecom.paymentservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.paymentservice.services.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {

    PaymentService paymentService;
    ObjectMapper objectMapper;

    public EventConsumer(PaymentService paymentService, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "order-service.order-created",
            groupId = "payment-service.consumers"
    )
    public void consumeOrderCreatedEvent(String message) {



        System.out.println("Order created event consumed: " + message);
    }

}
