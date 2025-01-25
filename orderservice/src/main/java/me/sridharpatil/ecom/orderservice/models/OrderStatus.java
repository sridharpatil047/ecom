package me.sridharpatil.ecom.orderservice.models;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED,
    CONFIRMED, // Payment confirmed
    PENDING,   // Payment pending
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED
}
