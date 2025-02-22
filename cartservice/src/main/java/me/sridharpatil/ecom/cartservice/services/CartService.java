package me.sridharpatil.ecom.cartservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.cartservice.exceptions.CartItemNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.CartNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.ProductAlreadyExistsException;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;

public interface CartService {
    Cart createCart(Long userId);
    Cart getCart(Long userId) throws CartNotFoundException;
    CartItem addItemToCart(Long userId, Long productId, int quantity) throws Exception;
    CartItem updateItemQuantity(Long userId, Long itemId, int quantity) throws CartNotFoundException, CartItemNotFoundException;
    void checkout(Long userId) throws JsonProcessingException, CartNotFoundException;
    void clearCart(Long userId) throws CartNotFoundException;


//    void removeItemFromCart(String userId, String productId);
//    void sendNotification(String userId, String productId, int quantity);
//    void sendEmailNotification(String userId, String productId, int quantity);
//    void sendSmsNotification(String userId, String productId, int quantity);
}
