package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;


import me.sridharpatil.ecom.notificationservice.models.EventType;

import java.util.Map;

public class OrderCancelledEventBasedTemplate extends EventBasedTemplate {

    public OrderCancelledEventBasedTemplate(EventType eventType, Map<String, String> variables) {
        super(eventType, variables);
    }

    @Override
    public String getSubject() {
        return "Order Cancelled";
    }

    @Override
    public String getBody() {
        return "Your order has been cancelled";
    }

    @Override
    public String getMessage() {
        return getBody();
    }
}
