package me.sridharpatil.ecom.userservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "me.sridharpatil.ecom.userservice.notification")
public class AppProps {
    private String type;
}
