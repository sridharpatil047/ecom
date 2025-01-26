package me.sridharpatil.ecom.paymentservice.services.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private Long userId;
    private String name;
    private String email;
}
