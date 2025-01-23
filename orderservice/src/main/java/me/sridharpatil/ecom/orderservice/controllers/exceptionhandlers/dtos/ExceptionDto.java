package me.sridharpatil.ecom.orderservice.controllers.exceptionhandlers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExceptionDto {
    private String errorCode;
    private String message;

    public ExceptionDto(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
