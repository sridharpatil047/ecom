package me.sridharpatil.ecom.userservice.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleNotFoundException extends Exception{
    public RoleNotFoundException(String message) {super(message);}
}
