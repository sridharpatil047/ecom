package me.sridharpatil.ecom.notificationservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "me.sridharpatil.ecom.notificationservice")
public class NotificationProperties {
    private String notifier;
}
