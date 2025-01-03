package me.sridharpatil.ecom.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
