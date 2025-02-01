package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;


import me.sridharpatil.ecom.notificationservice.services.EventType;

import java.util.Map;

public class OrderConfirmedEventBasedTemplate extends EventBasedTemplate {

    public OrderConfirmedEventBasedTemplate(EventType eventType, Map<String, String> variables) {
        super(eventType, variables);
    }

    @Override
    public String getSubject() {
        return "Order Confirmed";
    }

    @Override
    public String getBody() {
        return "Your order has been confirmed. Thank you for shopping with us.\nOrder ID: " + variables.get("orderId") + "\nTotal Amount: " + variables.get("totalPrice");
    }

    @Override
    public String getMessage() {
        return getBody();
    }
}
