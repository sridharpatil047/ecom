package me.sridharpatil.ecom.paymentservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "me.sridharpatil.ecom.paymentservice")
public class ConfigProperty {
    private String paymentGateway;
    private long paymentLinkExpiryInMinutes;
    private String razorpayKey;
    private String razorpaySecret;
}
