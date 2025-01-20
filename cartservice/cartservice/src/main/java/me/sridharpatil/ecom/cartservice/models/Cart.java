package me.sridharpatil.ecom.cartservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
public class Cart extends BaseModel{
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> items;

    private double totalPrice;
}
