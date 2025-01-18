package me.sridharpatil.ecom.cartservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {

    // 1. Create a new cart
    // POST /users/{userId}/cart


    // 2. Add item to cart
    // POST /users/{userId}/cart/items


    // 3. Remove item from cart
    // DELETE /users/{userId}/cart/items/{itemId}


    // 4. Get cart details
    // GET /users/{userId}/cart
}
