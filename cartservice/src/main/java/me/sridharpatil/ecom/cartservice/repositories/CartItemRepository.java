package me.sridharpatil.ecom.cartservice.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") Long cartId);
}
