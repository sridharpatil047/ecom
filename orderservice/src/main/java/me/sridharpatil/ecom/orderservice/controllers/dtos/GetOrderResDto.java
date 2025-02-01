package me.sridharpatil.ecom.orderservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.orderservice.models.Order;


@Getter @Setter
public class GetOrderResDto {
    private Long orderId;
    private Long userId;
    private Double total;

    public static GetOrderResDto of(Order order) {
        GetOrderResDto getOrderResDto = new GetOrderResDto();
        getOrderResDto.setOrderId(order.getId());
        getOrderResDto.setUserId(order.getUserId());
        getOrderResDto.setTotal(order.getTotalPrice());
        return getOrderResDto;
    }
}
