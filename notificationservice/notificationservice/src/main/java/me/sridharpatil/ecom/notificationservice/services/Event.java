package me.sridharpatil.ecom.notificationservice.services;

import lombok.Getter;

@Getter
public enum Event {
    USER_CREATED("USER_CREATED"),
    USER_UPDATED("USER_UPDATED"),
    USER_DELETED("USER_DELETED");

    private final String name;

    Event(String name) {
        this.name = name;
    }
}
