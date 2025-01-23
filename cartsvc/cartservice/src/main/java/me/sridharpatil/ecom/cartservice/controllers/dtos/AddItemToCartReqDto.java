package me.sridharpatil.ecom.cartservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddItemToCartReqDto {
    private Long productId;
    private double price;
    private int quantity;
}
