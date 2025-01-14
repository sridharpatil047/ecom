package me.sridharpatil.ecom.userservice.services;

import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExists;
import me.sridharpatil.ecom.userservice.models.User;

public interface UserService {
    User signUp(String name, String email, String password) throws UserAlreadyExists;
}
