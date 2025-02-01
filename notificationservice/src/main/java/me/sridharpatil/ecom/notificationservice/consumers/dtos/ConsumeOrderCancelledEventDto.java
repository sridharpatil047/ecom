package me.sridharpatil.ecom.notificationservice.consumers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConsumeOrderCancelledEventDto {
    private Long userId;
    private Long orderId;
}
