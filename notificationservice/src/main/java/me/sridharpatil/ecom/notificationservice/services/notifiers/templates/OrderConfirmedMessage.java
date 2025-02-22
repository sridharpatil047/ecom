package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class OrderConfirmedMessage implements Message{
    private Long orderId;
    private Double totalPrice;

    @Override
    public String getSubject() {
        return "Order Confirmed";
    }

    @Override
    public String getBody() {
        return "Your order has been confirmed. Thank you for shopping with us.\nOrder ID: " + orderId + "\nTotal Amount: " + totalPrice;
    }
}
