package me.sridharpatil.ecom.orderservice.services.producers;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.orderservice.models.Order;

@Getter @Setter
public class OrderCreatedEventDto {
    Long orderId;
    double amount;

    public static OrderCreatedEventDto of(Order order) {
        OrderCreatedEventDto orderCreatedEventDto = new OrderCreatedEventDto();
        orderCreatedEventDto.setOrderId(order.getId());
        orderCreatedEventDto.setAmount(order.getTotalPrice());
        return orderCreatedEventDto;
    }
}
