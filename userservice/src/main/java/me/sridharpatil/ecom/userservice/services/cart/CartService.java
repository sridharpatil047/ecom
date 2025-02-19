package me.sridharpatil.ecom.userservice.services.cart;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface CartService {
    Cart createCart(Long userId) throws JsonProcessingException;
}
