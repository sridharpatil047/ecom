package me.sridharpatil.ecom.cartservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateItemQuantityReqDto {
    private Long itemId;
    private int quantity;
}
