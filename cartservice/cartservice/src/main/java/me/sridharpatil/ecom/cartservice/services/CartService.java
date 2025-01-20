package me.sridharpatil.ecom.cartservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;

public interface CartService {
    Cart createCart(Long userId);
    Cart getCart(Long userId);
    CartItem addItemToCart(Long userId, Long productId, double price, int quantity);
    CartItem updateItemQuantity(Long userId, Long itemId, int quantity);
    void checkout(Long userId) throws JsonProcessingException;
    void clearCart(Long userId);


//    void removeItemFromCart(String userId, String productId);
//    void sendNotification(String userId, String productId, int quantity);
//    void sendEmailNotification(String userId, String productId, int quantity);
//    void sendSmsNotification(String userId, String productId, int quantity);
}
