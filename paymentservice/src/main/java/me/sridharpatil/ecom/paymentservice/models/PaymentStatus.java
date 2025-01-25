package me.sridharpatil.ecom.paymentservice.models;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    CREATED,    // Pending
    AUTHORIZED, //
    CAPTURED,   // Success
    REFUNDED,   // Refunded
    FAILED      // Failed
}

