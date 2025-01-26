package me.sridharpatil.ecom.paymentservice.services.stratagy;

import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;

public interface PaymentGateway {
    PaymentLink createPaymentLink(Payment payment) throws RazorpayException;
}
