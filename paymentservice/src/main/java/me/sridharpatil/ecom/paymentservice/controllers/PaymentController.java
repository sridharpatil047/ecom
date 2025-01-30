package me.sridharpatil.ecom.paymentservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.consumers.dtos.ConsumeOrderCreatedEventDto;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentMode;
import me.sridharpatil.ecom.paymentservice.models.PaymentStatus;
import me.sridharpatil.ecom.paymentservice.services.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    PaymentService paymentService;
    ObjectMapper objectMapper;

    public PaymentController(PaymentService paymentService, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/test")
    public void test() throws JsonProcessingException, RazorpayException {

//        String message = "{'orderId': 13, 'amount': 100.0}";
//        ConsumeOrderCreatedEventDto consumeOrderCreatedEventDto = objectMapper.readValue(message, ConsumeOrderCreatedEventDto.class);
        ConsumeOrderCreatedEventDto consumeOrderCreatedEventDto = new ConsumeOrderCreatedEventDto();
        consumeOrderCreatedEventDto.setOrderId(9L);
        consumeOrderCreatedEventDto.setAmount(0.0);


        Payment payment = paymentService.createPayment(
                consumeOrderCreatedEventDto.getOrderId(),
                consumeOrderCreatedEventDto.getAmount(),
                PaymentMode.CARD
        );


        paymentService.createPaymentLink(payment);

    }

    @GetMapping("/link")
    public ResponseEntity<String> getPaymentLink(@RequestParam Long orderId) {

        if (orderId != null) {
            return ResponseEntity.ok(paymentService.getPaymentLinkByOrderId(orderId).getLink());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<Payment> getPaymentStatus(@RequestParam Long paymentId) {
        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }

}
