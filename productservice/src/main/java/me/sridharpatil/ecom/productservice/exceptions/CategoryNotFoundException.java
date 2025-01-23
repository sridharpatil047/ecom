package me.sridharpatil.ecom.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
