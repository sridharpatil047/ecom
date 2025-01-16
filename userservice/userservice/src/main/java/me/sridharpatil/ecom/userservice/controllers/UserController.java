package me.sridharpatil.ecom.userservice.controllers;

import me.sridharpatil.ecom.userservice.authserver.models.JpaUserDetails;
import me.sridharpatil.ecom.userservice.controllers.dtos.*;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.UserService;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    ) throws UserAlreadyExistsException {

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
