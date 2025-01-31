package me.sridharpatil.ecom.orderservice.services.producers;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.orderservice.models.Order;



@Getter @Setter
public class OrderConfirmationReqDto {
    private Long userId;
    private Long orderId;
    private String status;

    public static OrderConfirmationReqDto of(Order order) {
        OrderConfirmationReqDto orderConfirmationReqDto = new OrderConfirmationReqDto();

        orderConfirmationReqDto.setUserId(order.getUserId());
        orderConfirmationReqDto.setOrderId(order.getId());
        orderConfirmationReqDto.setStatus(order.getStatus().name());

        return orderConfirmationReqDto;
    }

}
