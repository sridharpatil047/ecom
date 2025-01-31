package me.sridharpatil.ecom.orderservice.services.producers;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.orderservice.models.Order;

@Getter @Setter
public class OrderCancellationReqDto {
    private Long userId;
    private Long orderId;
    private String status;

    public static OrderCancellationReqDto of(Order order) {
        OrderCancellationReqDto orderCancellationReqDto = new OrderCancellationReqDto();
        orderCancellationReqDto.setUserId(order.getUserId());
        orderCancellationReqDto.setOrderId(order.getUserId());
        orderCancellationReqDto.setStatus(order.getStatus().name());
        return orderCancellationReqDto;
    }
}
