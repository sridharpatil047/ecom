package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UserCreatedMessage implements Message{
    private Long userId;
    private String name;

    @Override
    public String getSubject() {
        return "Welcome to Ecom";
    }

    @Override
    public String getBody() {
        return String.format("Welcome to Ecom %s! Your account has been created!", name);
    }
}
