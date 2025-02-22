package me.sridharpatil.ecom.userservice.services.dtos;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserDto {
    private List<String> roles;
    private String newPassword;
    private String oldPassword;
}
