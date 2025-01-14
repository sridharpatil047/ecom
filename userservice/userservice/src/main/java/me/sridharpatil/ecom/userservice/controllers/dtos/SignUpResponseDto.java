package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.userservice.models.User;

@Getter @Setter
public class SignUpResponseDto {
    private Long id;
    private String name;
    private String email;

    public static SignUpResponseDto of(User user) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setId(user.getId());
        signUpResponseDto.setName(user.getName());
        signUpResponseDto.setEmail(user.getEmail());
        return signUpResponseDto;
    }
}
