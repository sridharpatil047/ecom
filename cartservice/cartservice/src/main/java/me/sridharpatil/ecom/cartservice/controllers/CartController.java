package me.sridharpatil.ecom.cartservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.cartservice.controllers.dtos.AddItemToCartReqDto;
import me.sridharpatil.ecom.cartservice.controllers.dtos.UpdateItemQuantityReqDto;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;
import me.sridharpatil.ecom.cartservice.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {

    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 1. Create a new cart
    // POST /users/{userId}/cart
    @PostMapping()
    public ResponseEntity<Cart> createCart(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(cartService.createCart(userId));
    }

    // 2. Get cart details
    // GET /users/{userId}/cart
    @GetMapping()
    public ResponseEntity<Cart> getCart(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // 3. Add item to cart
    // POST /users/{userId}/cart/items
    @PostMapping("/items")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable("userId") Long userId, @RequestBody AddItemToCartReqDto reqDto) {
        return ResponseEntity.ok(cartService.addItemToCart(
                userId,
                reqDto.getProductId(),
                reqDto.getPrice(),
                reqDto.getQuantity()
        ));
    }

    // 4. Update cart item
    // PUT /users/{userId}/cart/items
    @PutMapping("/items")
    public ResponseEntity<CartItem> updateItemQuantity(@PathVariable("userId") Long userId, @RequestBody UpdateItemQuantityReqDto reqDto) {
        return ResponseEntity.ok(cartService.updateItemQuantity(
                userId,
                reqDto.getItemId(),
                reqDto.getQuantity()
        ));
    }

    // 5. Checkout cart
    // POST /users/{userId}/cart/checkout
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@PathVariable("userId") Long userId) throws JsonProcessingException {
        cartService.checkout(userId);
        return ResponseEntity.ok("Checkout successful");
    }

    // 6. Clear cart
    // DELETE /users/{userId}/cart
    @DeleteMapping()
    public ResponseEntity<String> clearCart(@PathVariable("userId") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }

}
