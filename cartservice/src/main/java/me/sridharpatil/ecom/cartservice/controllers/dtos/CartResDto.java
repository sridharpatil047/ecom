package me.sridharpatil.ecom.cartservice.controllers.dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;

import java.util.List;

@Getter @Setter
public class CartResDto {
    private Long cartId;
    private Long userId;
    private List<CartItemResDto> items;
    private double totalPrice;

    public static CartResDto of(Cart cart){
        CartResDto cartResDto = new CartResDto();

        cartResDto.setCartId(cart.getId());
        cartResDto.setUserId(cart.getUserId());
        cartResDto.setTotalPrice(cart.getTotalPrice());
        if (cart.getItems() != null) {
            List<CartItemResDto> cartItemResDtos
                    = cart.getItems()
                    .stream()
                    .map(CartItemResDto::of)
                    .toList();
            cartResDto.setItems(cartItemResDtos);
        }

        return cartResDto;
    }
}
