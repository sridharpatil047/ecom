package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class OrderCancelledMessage implements Message{

    @Override
    public String getSubject() {
        return "Order Cancelled";
    }

    @Override
    public String getBody() {
        return "Your order has been cancelled";
    }
}
