package me.sridharpatil.ecom.cartservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.cartservice.controllers.dtos.*;
import me.sridharpatil.ecom.cartservice.exceptions.CartItemNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.CartNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.ProductAlreadyExistsException;
import me.sridharpatil.ecom.cartservice.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 1. Create a new cart
    // POST /cart?userId={userId}
    @PostMapping()
    public ResponseEntity<CartResDto> createCart(@RequestParam(value = "userId") Long userId) {
        return ResponseEntity.ok(CartResDto.of(cartService.createCart(userId)));
    }

    // 2. Get cart details
    // GET /cart?userId={userId}
    @GetMapping()
    public ResponseEntity<CartResDto> getCart(@RequestParam(value = "userId") Long userId) throws CartNotFoundException {
        return ResponseEntity.ok(CartResDto.of(cartService.getCart(userId)));
    }

    // 3. Add item to cart
    // POST /cart/items?userId={userId}
    @PostMapping("/items")
    public ResponseEntity<CartItemResDto> addItemToCart(@RequestParam(value = "userId") Long userId, @RequestBody AddItemToCartReqDto reqDto) throws ProductAlreadyExistsException, CartNotFoundException {
        return ResponseEntity.ok(CartItemResDto.of(cartService.addItemToCart(
                userId,
                reqDto.getProductId(),
                reqDto.getPrice(),
                reqDto.getQuantity()
        )));
    }

    // 4. Update cart item
    // PUT /cart/items?userId={userId}
    @PutMapping("/items")
    public ResponseEntity<CartItemResDto> updateItemQuantity(@RequestParam(value = "userId") Long userId, @RequestBody UpdateItemQuantityReqDto reqDto) throws CartItemNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(CartItemResDto.of(cartService.updateItemQuantity(
                userId,
                reqDto.getItemId(),
                reqDto.getQuantity()
        )));
    }

    // 5. Checkout cart
    // POST /cart/checkout?userId={userId}
    @PostMapping("/checkout")
    public ResponseEntity<ResponseMessage> checkout(@RequestParam(value = "userId") Long userId) throws JsonProcessingException, CartNotFoundException {
        cartService.checkout(userId);
        return ResponseEntity.ok(new ResponseMessage(ResponseMessageType.SUCCESS, "Checkout successful"));
    }

    // 6. Clear cart
    // DELETE /cart?userId={userId}
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> clearCart(@RequestParam(value = "userId") Long userId) throws CartNotFoundException {
        cartService.clearCart(userId);
        return ResponseEntity.ok(new ResponseMessage(ResponseMessageType.SUCCESS, "Cart cleared successfully"));
    }

}
