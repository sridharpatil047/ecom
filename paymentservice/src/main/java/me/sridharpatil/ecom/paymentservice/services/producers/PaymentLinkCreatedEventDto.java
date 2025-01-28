package me.sridharpatil.ecom.paymentservice.services.producers;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;

import java.time.LocalDateTime;

@Getter @Setter
public class PaymentLinkCreatedEventDto {
    private Long orderId;
    private String identifier;
    private String link;
    private LocalDateTime expiry;

    public static PaymentLinkCreatedEventDto of(PaymentLink paymentLink) {
        PaymentLinkCreatedEventDto paymentLinkCreatedEventDto = new PaymentLinkCreatedEventDto();
        paymentLinkCreatedEventDto.setIdentifier(paymentLink.getIdentifier());
        paymentLinkCreatedEventDto.setLink(paymentLink.getLink());
        paymentLinkCreatedEventDto.setExpiry(paymentLink.getExpiry());
        paymentLinkCreatedEventDto.setOrderId(paymentLink.getPayment().getOrderId());
        return paymentLinkCreatedEventDto;
    }
}
