package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UpdateUserRolesRequestDto {
    private List<String> roles;
}
