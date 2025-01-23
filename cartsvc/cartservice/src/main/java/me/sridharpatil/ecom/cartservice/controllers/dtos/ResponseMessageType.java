package me.sridharpatil.ecom.cartservice.controllers.dtos;

import lombok.Getter;

@Getter
public enum ResponseMessageType {
    SUCCESS("Success"),
    ERROR("Error");

    private final String message;

    ResponseMessageType(String message) {
        this.message = message;
    }
}
