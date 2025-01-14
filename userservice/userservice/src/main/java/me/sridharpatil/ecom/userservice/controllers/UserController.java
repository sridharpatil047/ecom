package me.sridharpatil.ecom.userservice.controllers;

import me.sridharpatil.ecom.userservice.controllers.dtos.SignUpRequestDto;
import me.sridharpatil.ecom.userservice.controllers.dtos.SignUpResponseDto;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExists;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        try {
            User user = userService.signUp(
                    signUpRequestDto.getName(),
                    signUpRequestDto.getEmail(),
                    signUpRequestDto.getPassword()
            );
            return ResponseEntity.ok(SignUpResponseDto.of(user));
        }catch (UserAlreadyExists e){
            return ResponseEntity.badRequest().build();
        }
    }
}
