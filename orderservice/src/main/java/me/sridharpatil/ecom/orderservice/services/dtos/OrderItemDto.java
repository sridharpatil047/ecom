package me.sridharpatil.ecom.orderservice.services.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.orderservice.models.OrderItem;

@Builder
@Getter @Setter
public class OrderItemDto {
    private Long productId;
    private int quantity;
    private double price;
    private double subTotal;

    public static OrderItem toEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(orderItemDto.getProductId());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setSubTotal(orderItemDto.getSubTotal());
        return orderItem;
    }
}
