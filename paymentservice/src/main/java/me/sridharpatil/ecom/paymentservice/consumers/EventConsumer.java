package me.sridharpatil.ecom.paymentservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.paymentservice.consumers.dtos.ConsumeOrderCreatedEventDto;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentMode;
import me.sridharpatil.ecom.paymentservice.services.payment.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Log4j2
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
    public void consumeOrderCreatedEvent(@Payload String message) throws JsonProcessingException, RazorpayException {

        log.debug("Consuming order created event: " + message);
        ConsumeOrderCreatedEventDto consumeOrderCreatedEventDto = objectMapper.readValue(message, ConsumeOrderCreatedEventDto.class);

        Payment payment = paymentService.createPayment(
                consumeOrderCreatedEventDto.getOrderId(),
                consumeOrderCreatedEventDto.getAmount(),
                PaymentMode.CARD
        );
        log.info("Payment created for order: " + consumeOrderCreatedEventDto.getOrderId());

        paymentService.createPaymentLink(payment);
        log.info("Payment link created for order: " + consumeOrderCreatedEventDto.getOrderId());

    }
}
