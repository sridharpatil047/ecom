package me.sridharpatil.ecom.cartservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemNotFoundException extends Exception{
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
