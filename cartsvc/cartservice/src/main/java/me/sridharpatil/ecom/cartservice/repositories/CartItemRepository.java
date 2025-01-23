package me.sridharpatil.ecom.cartservice.repositories;

import me.sridharpatil.ecom.cartservice.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
