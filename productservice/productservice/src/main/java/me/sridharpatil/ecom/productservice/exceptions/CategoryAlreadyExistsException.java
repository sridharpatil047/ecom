package me.sridharpatil.ecom.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryAlreadyExistsException extends Exception{
    private String message;

    public CategoryAlreadyExistsException(String message) {
        this.message = message;
    }
}
