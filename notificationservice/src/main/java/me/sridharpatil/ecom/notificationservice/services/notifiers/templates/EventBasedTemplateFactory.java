package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;

import me.sridharpatil.ecom.notificationservice.models.EventType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventBasedTemplateFactory {
    public EventBasedTemplate getTemplate(EventType eventType, Map<String, String> variables) {
        if (eventType.equals(EventType.USER_CREATED)) {
            return new UserCreatedEventBasedTemplate(eventType, variables);
        } else if (eventType.equals(EventType.ORDER_CANCELLED)) {
            return new OrderCancelledEventBasedTemplate(eventType, variables);
        } else if (eventType.equals(EventType.ORDER_CONFIRMED)) {
            return new OrderConfirmedEventBasedTemplate(eventType, variables);
        } else {
            return null;
        }
    }
}
