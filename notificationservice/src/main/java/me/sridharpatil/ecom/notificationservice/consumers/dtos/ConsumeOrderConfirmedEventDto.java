package me.sridharpatil.ecom.notificationservice.consumers.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ConsumeOrderConfirmedEventDto {
    private Long userId;
    private Long orderId;
    private String status;
}
