package me.sridharpatil.ecom.paymentservice.services.payment.paymentgateways;

import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.properties.ConfigProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentGatewayContext {

    private final PaymentGateway paymentGateway;

    public PaymentGatewayContext(Map<String, PaymentGateway> paymentGateways, ConfigProperty configProperty) {
        this.paymentGateway = paymentGateways.get(configProperty.getPaymentGateway());
    }
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException {
        return paymentGateway.createPaymentLink(payment);
    }

}
