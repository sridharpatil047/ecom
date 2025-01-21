package me.sridharpatil.ecom.cartservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartNotFoundException extends Exception{
    public CartNotFoundException(String message) {
        super(message);
    }
}
