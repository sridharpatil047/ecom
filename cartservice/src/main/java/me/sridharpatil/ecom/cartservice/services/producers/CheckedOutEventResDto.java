package me.sridharpatil.ecom.cartservice.services.producers;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.cartservice.models.Cart;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CheckedOutEventResDto {
    private Long userId;
    private List<Item> items;
    private double totalPrice;

    @Getter @Setter
    public static class Item {
        private Long productId;
        private int quantity;
        private double price;
        private double subTotal;
    }

    public static CheckedOutEventResDto of(Cart cart) {
        CheckedOutEventResDto checkedOutEventResDto = new CheckedOutEventResDto();

        checkedOutEventResDto.setUserId(cart.getUserId());
        checkedOutEventResDto.setTotalPrice(cart.getTotalPrice());

        List<Item> items = new ArrayList<>();
        cart.getItems().forEach(cartItem -> {
            Item item = new Item();
            item.setProductId(cartItem.getProductId());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getPrice());
            item.setSubTotal(cartItem.getSubTotal());
            items.add(item);
        });
        checkedOutEventResDto.setItems(items);

        return checkedOutEventResDto;

    }

}
