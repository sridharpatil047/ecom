package me.sridharpatil.ecom.notificationservice.services;

import lombok.Getter;

@Getter
public enum EventType {
    USER_CREATED("USER_CREATED"),
    USER_UPDATED("USER_UPDATED"),
    USER_DELETED("USER_DELETED"),
    ORDER_CANCELLED("ORDER_CANCELLED"),
    ORDER_CONFIRMED("ORDER_CONFIRMED");

    private final String name;

    EventType(String name) {
        this.name = name;
    }
}
