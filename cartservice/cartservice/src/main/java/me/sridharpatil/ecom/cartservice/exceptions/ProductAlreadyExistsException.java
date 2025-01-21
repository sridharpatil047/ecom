package me.sridharpatil.ecom.cartservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductAlreadyExistsException extends Exception{
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
