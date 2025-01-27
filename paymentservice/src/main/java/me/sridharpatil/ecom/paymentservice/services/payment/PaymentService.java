package me.sridharpatil.ecom.paymentservice.services.payment;

import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.models.PaymentMode;
import me.sridharpatil.ecom.paymentservice.models.PaymentStatus;

public interface PaymentService {
    PaymentLink createPaymentLink(Payment payment) throws RazorpayException;
    PaymentLink getPaymentLink(String link);
    Payment createPayment(Long orderId, Double amount, PaymentMode paymentMode);
    Payment getPayment(Long paymentId);
    void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus);
}
