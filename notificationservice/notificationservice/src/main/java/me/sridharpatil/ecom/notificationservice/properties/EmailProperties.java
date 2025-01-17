package me.sridharpatil.ecom.notificationservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter @Setter
@Component
@ConfigurationProperties(prefix = "me.sridharpatil.ecom.notificationservice.email")
public class EmailProperties {
    private String host;
    private String port;
    private String username;
    private String emailId;
    private String password;
    private String enableAuth;
    private String enableTls;
}
