package me.sridharpatil.ecom.userservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.userservice.controllers.dtos.*;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.UserService;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    ) throws UserAlreadyExistsException, JsonProcessingException {

        User user = userService.signUp(
                signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword()
        );
        return ResponseEntity.ok(SignUpResponseDto.of(user));
    }

    // 2. PATCH /users/{id}
    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.claims.get('user_id') or hasRole('ROLE_ADMIN')")
    ResponseEntity<String> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UpdateUserReqDto requestDto
            ) throws RoleNotFoundException, UserNotFoundException {

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
    ResponseEntity<String> updateUserRoles(
            @PathVariable("id") Long id,
            @RequestBody UpdateUserRolesRequestDto requestDto
    ) throws UserNotFoundException, RoleNotFoundException {

        // Create UserDto from requestDto
        UserDto userDto = new UserDto();
        userDto.setRoles(requestDto.getRoles());

        // Update user roles
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("User roles updated successfully");
    }


    // 3. POST /users/{id}/password-reset/request
    @PostMapping("/{id}/password-reset/request")
    ResponseEntity<String> requestPasswordReset(@PathVariable("id") Long id) {
        // Request password reset
        // TODO : Implement this
//        userService.requestPasswordReset(Long id);
        return ResponseEntity.ok("A password reset link sent to your email ");
    }

    // 4. POST /users/{id}/password-reset/confirm
    @PostMapping("/{id}/password-reset/confirm")
    ResponseEntity<String> confirmPasswordReset(@PathVariable("id") Long id) {
        // Confirm password reset
        // TODO : Implement this
//        userService.confirmPasswordReset(reqDto.getEmail(), reqDto.getOtp(), reqDto.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
}
