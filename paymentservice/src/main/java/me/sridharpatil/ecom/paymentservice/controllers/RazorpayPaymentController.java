package me.sridharpatil.ecom.paymentservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.services.payment.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payments/razorpay")
public class RazorpayPaymentController {

    PaymentService paymentService;

    public RazorpayPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/callback")
    public void handleCallback(
            @RequestParam("razorpay_payment_id") String razorpayPaymentId,
            @RequestParam("razorpay_payment_link_id")String razorpayPaymentLinkId
    ) throws RazorpayException, JsonProcessingException {
        // handle callback
//        razorpayPaymentGatewayAdapter.handleCallback(razorpayPaymentId, razorpayPaymentLinkId);
        Map<String, String> payload = Map.of(
                "razorpay_payment_id", razorpayPaymentId,
                "razorpay_payment_link_id", razorpayPaymentLinkId
        );
        paymentService.handleCallback(payload);

        System.out.println("Payment Id : " + razorpayPaymentId);
    }
}
