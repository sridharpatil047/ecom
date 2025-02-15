package me.sridharpatil.ecom.userservice.controllers;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.userservice.controllers.dtos.GetShippingAddressesResDto;
import me.sridharpatil.ecom.userservice.controllers.dtos.GetUserResDto;
import me.sridharpatil.ecom.userservice.exceptions.ShippingAddressNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/private/users")
public class UserInternalController {
    private final UserService userService;

    public UserInternalController(UserService userService) {
        this.userService = userService;
    }

    // 1. GET private/users/{id}/shipping-addresses
    @GetMapping("/{id}/shipping-addresses")
    ResponseEntity<List<GetShippingAddressesResDto>> getShippingAddresses(@PathVariable("id") Long id) throws ShippingAddressNotFoundException {
        log.info("getShippingAddresses called");
        return ResponseEntity.ok(
                userService.getShippingAddresses(id)
                        .stream()
                        .map(GetShippingAddressesResDto::of)
                        .collect(Collectors.toList())
        );
    }

    // 6. GET private/users/{id}
    @GetMapping("/{id}")
    ResponseEntity<GetUserResDto> getUser(@PathVariable("id") Long id) throws UserNotFoundException {
        return ResponseEntity.ok(GetUserResDto.of(userService.getUser(id)));
    }

}
