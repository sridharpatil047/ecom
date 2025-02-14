package me.sridharpatil.ecom.orderservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
@Builder
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel implements Serializable {
    private Long userId;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
