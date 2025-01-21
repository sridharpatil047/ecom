package me.sridharpatil.ecom.cartservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseMessage {
    private ResponseMessageType status;
    private String message;

    public ResponseMessage(ResponseMessageType status, String message) {
        this.status = status;
        this.message = message;
    }
}
