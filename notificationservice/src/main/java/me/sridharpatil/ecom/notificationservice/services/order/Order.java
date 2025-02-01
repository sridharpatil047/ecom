package me.sridharpatil.ecom.notificationservice.services.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Order {
    private Long orderId;
    private Long userId;
    private Double total;
}
