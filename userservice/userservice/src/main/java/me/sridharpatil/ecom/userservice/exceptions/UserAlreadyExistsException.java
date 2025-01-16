package me.sridharpatil.ecom.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
