package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.userservice.models.User;

@Getter @Setter
public class GetUserResDto {
    private Long userId;
    private String name;
    private String email;

    public static GetUserResDto of(User user) {
        GetUserResDto getUserResDto = new GetUserResDto();
        getUserResDto.setUserId(user.getId());
        getUserResDto.setName(user.getName());
        getUserResDto.setEmail(user.getEmail());
        return getUserResDto;
    }
}
