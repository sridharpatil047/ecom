package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;


import me.sridharpatil.ecom.notificationservice.services.EventType;

import java.util.Map;

public class UserCreatedEventBasedTemplate extends EventBasedTemplate {

    public UserCreatedEventBasedTemplate(EventType eventType, Map<String, String> variables) {
        super(eventType, variables);
    }

    @Override
    public String getSubject() {
        return "Welcome to Ecom";
    }

    @Override
    public String getBody() {
        return "Welcome to Ecom, " + variables.get("userName") + ". You have successfully signed up.";
    }

    @Override
    public String getMessage() {
        return getBody();
    }
}
