package me.sridharpatil.ecom.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleAlreadyExistsException extends Exception{
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
