package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUserRequestDto {
    private String name;
    private String email;
    private String password;
}
