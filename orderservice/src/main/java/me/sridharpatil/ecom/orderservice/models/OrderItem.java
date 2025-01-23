package me.sridharpatil.ecom.orderservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class OrderItem extends BaseModel {
    private Long productId;
    private int quantity;
    private double price;
    private double subTotal;

    @ManyToOne
    private Order order;
}
