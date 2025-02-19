package me.sridharpatil.ecom.notificationservice.consumers.dtos;

import lombok.Data;

@Data
public class ConsumePasswordResetEventDto {
    Long userId;
    Integer otp;
}
