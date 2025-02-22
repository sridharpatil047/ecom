package me.sridharpatil.ecom.cartservice.controllers.dtos;

import lombok.*;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CreateCartReqDto {
    private Long userId;
}
