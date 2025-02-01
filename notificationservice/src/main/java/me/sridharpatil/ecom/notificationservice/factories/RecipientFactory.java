package me.sridharpatil.ecom.notificationservice.factories;

import me.sridharpatil.ecom.notificationservice.dtos.EmailRecipient;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.services.user.User;
import org.springframework.stereotype.Component;

@Component
public class RecipientFactory {
    public static Recipient getRecipient(User user, String type) {
        if (type.equals("email")) {
            return EmailRecipient.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        } else if (type.equals("sms")) {
            return null;
        } else {
            throw new RuntimeException("Invalid notifier");
        }
    }
}
