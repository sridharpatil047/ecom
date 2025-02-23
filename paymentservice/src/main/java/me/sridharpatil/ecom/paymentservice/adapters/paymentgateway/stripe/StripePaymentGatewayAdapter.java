package me.sridharpatil.ecom.paymentservice.adapters.paymentgateway.stripe;

import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.adapters.paymentgateway.PaymentGatewayAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(value = "stripe")
public class StripePaymentGatewayAdapter implements PaymentGatewayAdapter {
    @Override
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException {
        return null;
    }

    @Override
    public void handleCallback(Map<String, String> payload) {
    }
//    @Override
//    PaymentLink createPaymentLink(Payment payment) throws RazorpayException {
//        // TODO: To be implemented
//        return null;
//    }
}
