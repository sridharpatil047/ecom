package me.sridharpatil.ecom.cartservice.services;

import me.sridharpatil.ecom.cartservice.models.Cart;

public interface CartService {
    Cart createCart(Long userId);
    void addItemToCart(Long userId, Long productId, double price, int quantity);
    void updateItemQuantity(Long userId, Long productId, int quantity);
//    void removeItemFromCart(String userId, String productId);
//    void clearCart(String userId);
//    void checkout(String userId);
//    void sendNotification(String userId, String productId, int quantity);
//    void sendEmailNotification(String userId, String productId, int quantity);
//    void sendSmsNotification(String userId, String productId, int quantity);
}
