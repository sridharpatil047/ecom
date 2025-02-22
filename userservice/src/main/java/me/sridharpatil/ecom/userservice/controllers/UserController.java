package me.sridharpatil.ecom.userservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.userservice.controllers.dtos.*;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.ShippingAddressNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.ShippingAddress;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.UserService;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;
    HttpServletRequest httpServletRequest;


    public UserController(UserService userService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    // 1. POST /users
    @PostMapping()
    ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) throws UserAlreadyExistsException, JsonProcessingException, UserNotFoundException, RoleNotFoundException {
        User user = userService.signUp(
                createUserRequestDto.getName(),
                createUserRequestDto.getEmail(),
                createUserRequestDto.getPassword()
        );
        return ResponseEntity.ok(CreateUserResponseDto.of(user));
    }

    // 2. PATCH /users/{id}
    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.claims.get('user_id') or hasRole('ROLE_ADMIN')")
    ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserReqDto requestDto) throws RoleNotFoundException, UserNotFoundException {
        // Create UserDto from requestDto
        UserDto userDto = new UserDto();
        userDto.setNewPassword(requestDto.getPassword());

        // Update user
        userService.updateUser(id, userDto);
        System.out.println();
        return ResponseEntity.ok("User password updated successfully");
    }

    // 3. PATCH /users/{id}/roles
    @PatchMapping("/{id}/roles")
    ResponseEntity<String> updateUserRoles(@PathVariable("id") Long id, @RequestBody UpdateUserRolesRequestDto requestDto) throws UserNotFoundException, RoleNotFoundException {
        // Create UserDto from requestDto
        UserDto userDto = new UserDto();
        userDto.setRoles(requestDto.getRoles());

        // Update user roles
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("User roles updated successfully");
    }



    // 5. PUT /users/{id}/password
    @PutMapping("/{id}/password")
    ResponseEntity<String> changePassword(@PathVariable("id") Long id, @RequestBody UserDto userDto) throws UserNotFoundException, RoleNotFoundException {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("Password updated successfully");
    }

    // POST /users/{id}/shipping-addresses
    @PostMapping("/{id}/shipping-addresses")
    ResponseEntity<String> addShippingAddress(@PathVariable("id") Long id, @RequestBody AddShippingAddressReqDto requestDto) {
        // Add shipping address
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setCountry(requestDto.getCountry());
        shippingAddress.setState(requestDto.getState());
        shippingAddress.setCity(requestDto.getCity());
        shippingAddress.setStreet(requestDto.getStreet());
        shippingAddress.setPinCode(requestDto.getPinCode());

        userService.addShippingAddress(id, shippingAddress);
        return ResponseEntity.ok("Shipping address added successfully");
    }

}
