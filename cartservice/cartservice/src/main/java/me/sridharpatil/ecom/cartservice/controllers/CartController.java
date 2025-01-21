package me.sridharpatil.ecom.cartservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.cartservice.controllers.dtos.AddItemToCartReqDto;
import me.sridharpatil.ecom.cartservice.controllers.dtos.CartItemResDto;
import me.sridharpatil.ecom.cartservice.controllers.dtos.CartResDto;
import me.sridharpatil.ecom.cartservice.controllers.dtos.UpdateItemQuantityReqDto;
import me.sridharpatil.ecom.cartservice.exceptions.CartItemNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.CartNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.ProductAlreadyExistsException;
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
    public ResponseEntity<CartResDto> createCart(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(CartResDto.of(cartService.createCart(userId)));
    }

    // 2. Get cart details
    // GET /users/{userId}/cart
    @GetMapping()
    public ResponseEntity<CartResDto> getCart(@PathVariable("userId") Long userId) throws CartNotFoundException {
        return ResponseEntity.ok(CartResDto.of(cartService.getCart(userId)));
    }

    // 3. Add item to cart
    // POST /users/{userId}/cart/items
    @PostMapping("/items")
    public ResponseEntity<CartItemResDto> addItemToCart(@PathVariable("userId") Long userId, @RequestBody AddItemToCartReqDto reqDto) throws ProductAlreadyExistsException, CartNotFoundException {
        return ResponseEntity.ok(CartItemResDto.of(cartService.addItemToCart(
                userId,
                reqDto.getProductId(),
                reqDto.getPrice(),
                reqDto.getQuantity()
        )));
    }

    // 4. Update cart item
    // PUT /users/{userId}/cart/items
    @PutMapping("/items")
    public ResponseEntity<CartItemResDto> updateItemQuantity(@PathVariable("userId") Long userId, @RequestBody UpdateItemQuantityReqDto reqDto) throws CartItemNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(CartItemResDto.of(cartService.updateItemQuantity(
                userId,
                reqDto.getItemId(),
                reqDto.getQuantity()
        )));
    }

    // 5. Checkout cart
    // POST /users/{userId}/cart/checkout
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@PathVariable("userId") Long userId) throws JsonProcessingException, CartNotFoundException {
        cartService.checkout(userId);
        return ResponseEntity.ok("Checkout successful");
    }

    // 6. Clear cart
    // DELETE /users/{userId}/cart
    @DeleteMapping()
    public ResponseEntity<String> clearCart(@PathVariable("userId") Long userId) throws CartNotFoundException {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }

}
