package me.sridharpatil.ecom.paymentservice.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.properties.ConfigProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfig {
    ConfigProperty configProperty;

    public PaymentGatewayConfig(ConfigProperty configProperty) {
        this.configProperty = configProperty;
    }

    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient(
                configProperty.getRazorpayKey(),
                configProperty.getRazorpaySecret()
        );
    }
}
