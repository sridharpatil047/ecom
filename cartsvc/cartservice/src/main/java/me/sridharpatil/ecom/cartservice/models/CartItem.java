package me.sridharpatil.ecom.cartservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@Entity
public class CartItem extends BaseModel implements Serializable {
    private Long productId;
    private int quantity;
    private double price;
    private double subTotal;
}
