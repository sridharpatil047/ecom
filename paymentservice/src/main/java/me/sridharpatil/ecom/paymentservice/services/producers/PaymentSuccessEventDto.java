package me.sridharpatil.ecom.paymentservice.services.producers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentSuccessEventDto {
    private Long orderId;

    public static PaymentSuccessEventDto of(Long orderId) {
        PaymentSuccessEventDto paymentSuccessEventDto = new PaymentSuccessEventDto();
        paymentSuccessEventDto.setOrderId(orderId);
        return paymentSuccessEventDto;
    }
}
