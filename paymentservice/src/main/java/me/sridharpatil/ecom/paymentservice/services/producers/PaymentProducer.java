package me.sridharpatil.ecom.paymentservice.services.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    public PaymentProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void paymentLinkCreatedEvent(PaymentLink paymentLink) throws JsonProcessingException {
        PaymentLinkCreatedEventDto paymentLinkCreatedEventDto = PaymentLinkCreatedEventDto.of(paymentLink);
        kafkaTemplate.send(
                "payment-service.payment-link-created",
                paymentLinkCreatedEventDto.getOrderId().toString(),
                objectMapper.writeValueAsString(paymentLinkCreatedEventDto)
        );
    }
}
