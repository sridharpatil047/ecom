package me.sridharpatil.ecom.userservice.services.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserDto {
    private List<String> roles;
    private String newPassword;
    private String oldPassword;
}
