package me.sridharpatil.ecom.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.userservice.controllers.dtos.AddShippingAddressReqDto;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.ShippingAddressNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.ShippingAddress;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;

import java.util.List;

public interface UserService {
    User signUp(String name, String email, String password) throws UserAlreadyExistsException, JsonProcessingException;
    void updateUser(Long userId, UserDto userDto) throws RoleNotFoundException, UserNotFoundException;
    List<ShippingAddress> getShippingAddresses(Long userId) throws ShippingAddressNotFoundException;
    User getUser(Long userId) throws UserNotFoundException;
    void addShippingAddress(Long id, ShippingAddress shippingAddress);
}
