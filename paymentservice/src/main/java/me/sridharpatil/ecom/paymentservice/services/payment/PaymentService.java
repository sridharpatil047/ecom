package me.sridharpatil.ecom.paymentservice.services.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.models.PaymentMode;
import me.sridharpatil.ecom.paymentservice.models.PaymentStatus;

public interface PaymentService {
    PaymentLink createPaymentLink(Payment payment) throws RazorpayException, JsonProcessingException;
    PaymentLink getPaymentLinkByOrderId(Long orderId);
    Payment createPayment(Long orderId, Double amount, PaymentMode paymentMode);
    Payment getPayment(Long paymentId);
    void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus);
}
