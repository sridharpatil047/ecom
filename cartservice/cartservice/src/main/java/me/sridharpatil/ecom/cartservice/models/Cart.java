package me.sridharpatil.ecom.cartservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
public class Cart extends BaseModel{
    private Long userId;

    @OneToMany
    private List<CartItem> items;

    private double totalPrice;
}
