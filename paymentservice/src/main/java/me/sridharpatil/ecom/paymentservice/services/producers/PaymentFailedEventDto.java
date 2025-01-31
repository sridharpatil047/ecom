package me.sridharpatil.ecom.paymentservice.services.producers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentFailedEventDto {
    private Long orderId;
    private String reason;

    public static PaymentFailedEventDto of(Long orderId, String reason) {
        PaymentFailedEventDto paymentFailedEventDto = new PaymentFailedEventDto();
        paymentFailedEventDto.setOrderId(orderId);
        paymentFailedEventDto.setReason(reason);
        return paymentFailedEventDto;
    }
}
