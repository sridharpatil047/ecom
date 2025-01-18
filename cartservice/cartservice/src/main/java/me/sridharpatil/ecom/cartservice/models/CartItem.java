package me.sridharpatil.ecom.cartservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class CartItem extends BaseModel{
    private Long productId;
    private int quantity;
    private double price;
    private double subTotal;
}
