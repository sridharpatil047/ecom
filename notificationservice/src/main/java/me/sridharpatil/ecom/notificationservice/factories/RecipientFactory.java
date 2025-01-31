package me.sridharpatil.ecom.notificationservice.factories;

import me.sridharpatil.ecom.notificationservice.dtos.EmailRecipient;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import org.springframework.stereotype.Component;

@Component
public class RecipientFactory {
    public static Recipient getRecipient(Long userId, String type) {
        if (type.equals("email")) {
            return EmailRecipient.builder()
                    .name("John Doe")
                    .email(userId.toString())
                    .build();
        } else if (type.equals("sms")) {
            return null;
        } else {
            throw new RuntimeException("Invalid notifier");
        }
    }
}
