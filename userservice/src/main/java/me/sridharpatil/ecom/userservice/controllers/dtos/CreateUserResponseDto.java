package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.userservice.models.User;

@Getter @Setter
public class CreateUserResponseDto {
    private Long id;
    private String name;
    private String email;

    public static CreateUserResponseDto of(User user) {
        CreateUserResponseDto createUserResponseDto = new CreateUserResponseDto();
        createUserResponseDto.setId(user.getId());
        createUserResponseDto.setName(user.getName());
        createUserResponseDto.setEmail(user.getEmail());
        return createUserResponseDto;
    }
}
