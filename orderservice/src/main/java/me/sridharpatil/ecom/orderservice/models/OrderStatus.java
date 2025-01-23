package me.sridharpatil.ecom.orderservice.models;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED
}
