package me.sridharpatil.ecom.orderservice.consumers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderCancellationReqDto {
    Long orderId;
    String reason;
}
