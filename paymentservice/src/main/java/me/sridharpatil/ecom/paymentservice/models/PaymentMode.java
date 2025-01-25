package me.sridharpatil.ecom.paymentservice.models;

import lombok.Getter;

@Getter
public enum PaymentMode {
    CARD,
    UPI,
    NETBANKING
}
