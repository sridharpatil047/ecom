package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConfirmPasswordResetReqDto {
    private String newPassword;
}
