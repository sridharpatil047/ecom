package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;

import me.sridharpatil.ecom.notificationservice.models.EventType;

import java.util.Map;

public abstract class EventBasedTemplate {

    EventType eventType;
    Map<String, String> variables;

    public EventBasedTemplate(EventType eventType, Map<String, String> variables) {
        this.eventType = eventType;
        this.variables = variables;
    }

    public abstract String getSubject();
    public abstract String getBody();
    public abstract String getMessage();
}
