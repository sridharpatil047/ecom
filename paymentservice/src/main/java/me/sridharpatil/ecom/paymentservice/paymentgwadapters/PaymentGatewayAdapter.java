package me.sridharpatil.ecom.paymentservice.paymentgwadapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;

import java.util.Map;

public interface PaymentGatewayAdapter {
    PaymentLink createPaymentLink(Payment payment) throws RazorpayException, JsonProcessingException;
    void handleCallback(Map<String, String> payload) throws RazorpayException, JsonProcessingException;
}
