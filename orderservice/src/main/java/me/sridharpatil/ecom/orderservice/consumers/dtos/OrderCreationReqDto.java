package me.sridharpatil.ecom.orderservice.consumers.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderCreationReqDto {
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

}
