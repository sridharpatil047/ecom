package me.sridharpatil.ecom.cartservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.cartservice.models.CartItem;

@Getter @Setter
public class CartItemResDto {
    private Long id;
    private Long productId;
    private int quantity;
    private double price;
    private double subTotal;

    public static CartItemResDto of(CartItem cartItem){
        CartItemResDto cartItemResDto = new CartItemResDto();
        cartItemResDto.setId(cartItem.getId());
        cartItemResDto.setProductId(cartItem.getProductId());
        cartItemResDto.setQuantity(cartItem.getQuantity());
        cartItemResDto.setPrice(cartItem.getPrice());
        cartItemResDto.setSubTotal(cartItem.getSubTotal());
        return cartItemResDto;
    }
}
