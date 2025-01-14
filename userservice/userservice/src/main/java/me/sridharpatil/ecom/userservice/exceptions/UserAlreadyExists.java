package me.sridharpatil.ecom.userservice.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
