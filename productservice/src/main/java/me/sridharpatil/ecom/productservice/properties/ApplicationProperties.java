package me.sridharpatil.ecom.productservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties("me.sridharpatil.ecom.productservice")
public class ApplicationProperties {
    public String jwtIssuerService;
    public String jwtIssuerEndpoint;
}
