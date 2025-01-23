package me.sridharpatil.ecom.orderservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "orders")
public class Order extends BaseModel{
    private Long userId;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
