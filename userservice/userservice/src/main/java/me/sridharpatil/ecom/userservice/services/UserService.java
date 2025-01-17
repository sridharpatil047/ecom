package me.sridharpatil.ecom.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;

public interface UserService {
    User signUp(String name, String email, String password) throws UserAlreadyExistsException, JsonProcessingException;

    void updateUser(Long userId, UserDto userDto) throws RoleNotFoundException, UserNotFoundException;
}
