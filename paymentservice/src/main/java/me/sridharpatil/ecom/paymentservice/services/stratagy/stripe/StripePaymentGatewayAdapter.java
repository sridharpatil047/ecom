package me.sridharpatil.ecom.paymentservice.services.stratagy.stripe;

import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.services.stratagy.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGatewayAdapter implements PaymentGateway {
    @Override
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException {
        return null;
    }
//    @Override
//    PaymentLink createPaymentLink(Payment payment) throws RazorpayException {
//        // TODO: To be implemented
//        return null;
//    }
}
