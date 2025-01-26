package me.sridharpatil.ecom.paymentservice.services.stratagy;

import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.properties.ConfigProperty;
import me.sridharpatil.ecom.paymentservice.services.stratagy.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentGatewayContext {

    private PaymentGateway paymentGateway;
    private ConfigProperty configProperty;

    public PaymentGatewayContext(Map<String, PaymentGateway> paymentGateways, ConfigProperty configProperty) {
        this.configProperty = configProperty;
        this.paymentGateway = paymentGateways.get(configProperty.getPaymentGateway());
    }
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException {
        return paymentGateway.createPaymentLink(payment);
    }

}
