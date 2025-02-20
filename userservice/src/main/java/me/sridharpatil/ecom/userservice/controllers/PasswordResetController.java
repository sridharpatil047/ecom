package me.sridharpatil.ecom.userservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.userservice.controllers.dtos.ConfirmPasswordResetReqDto;
import me.sridharpatil.ecom.userservice.controllers.dtos.RequestPasswordResetReqDto;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/password-resets")
public class PasswordResetController {

    private final UserService userService;

    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    // 1. POST /password-resets
    @PostMapping
    ResponseEntity<String> requestPasswordReset(@RequestBody RequestPasswordResetReqDto reqDto) throws UserNotFoundException, JsonProcessingException {
        log.debug("Request to request password reset for user {}", reqDto.getEmail());
        userService.requestPasswordReset(reqDto.getEmail());
        return ResponseEntity.ok("OTP sent to your email");
    }

    // 2. PUT /password-resets/{token}
    @PutMapping("/{token}")
    ResponseEntity<String> confirmPasswordReset(@PathVariable Integer token, @RequestBody ConfirmPasswordResetReqDto dto) throws UserNotFoundException, JsonProcessingException {
        User user = userService.confirmPasswordReset(token, dto.getNewPassword());
        return ResponseEntity.ok("Password is reset successfully");
    }
}
