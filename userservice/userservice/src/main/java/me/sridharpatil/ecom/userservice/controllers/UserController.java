package me.sridharpatil.ecom.userservice.controllers;

import me.sridharpatil.ecom.userservice.controllers.dtos.*;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExists;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.UserService;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. POST /signup
    @PostMapping("/sign-up")
    ResponseEntity<SignUpResponseDto> signUp(
            @RequestBody
            SignUpRequestDto signUpRequestDto
    ) throws UserAlreadyExists {

        User user = userService.signUp(
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword()
        );
        return ResponseEntity.ok(SignUpResponseDto.of(user));
    }

    // 2. PATCH /users/{id}
    @PatchMapping("/{id}")
    ResponseEntity<User> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UpdateUserRequestDto requestDto
            ) throws RoleNotFoundException, UserNotFoundException {

        // Create UserDto from requestDto
        UserDto userDto = new UserDto();
        userDto.setRoles(requestDto.getRoles());
        userDto.setNewPassword(requestDto.getNewPassword());

        // Update user
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }


    // 3. POST /users/password-reset/request
    @PostMapping("/password-reset/request")
    ResponseEntity<String> requestPasswordReset(
            @RequestBody
            RequestPasswordResetReqDto reqDto
    ) {
        // Request password reset
        // TODO : Implement this
//        userService.requestPasswordReset(reqDto.getEmail());
        return ResponseEntity.ok("A password reset link sent to your email " + reqDto.getEmail());
    }

    // 4. POST /users/password-reset/confirm
    @PostMapping("/password-reset/confirm")
    ResponseEntity<String> confirmPasswordReset(
            @RequestBody
            ConfirmPasswordResetReqDto reqDto
    ) {
        // Confirm password reset
        // TODO : Implement this
//        userService.confirmPasswordReset(reqDto.getEmail(), reqDto.getOtp(), reqDto.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }

}
