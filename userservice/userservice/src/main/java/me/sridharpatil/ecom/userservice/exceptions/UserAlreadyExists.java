package me.sridharpatil.ecom.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
