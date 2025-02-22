package me.sridharpatil.ecom.cartservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.cartservice.contexts.authentication.JwtUserAuthentication;
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
    JwtUserAuthentication userAuthentication;

    public CartController(CartService cartService, JwtUserAuthentication userAuthentication) {
        this.cartService = cartService;
        this.userAuthentication = userAuthentication;
    }

    // 2. GET /cart
    @GetMapping()
    public ResponseEntity<CartResDto> getCart() throws CartNotFoundException {
        return ResponseEntity.ok(CartResDto.of(cartService.getCart(userAuthentication.getUserId())));
    }

    // 3. POST /cart/items
    @PostMapping("/items")
    public ResponseEntity<CartItemResDto> addItemToCart(@RequestBody AddItemToCartReqDto reqDto) throws Exception {
        return ResponseEntity.ok(CartItemResDto.of(cartService.addItemToCart(
                userAuthentication.getUserId(),
                reqDto.getProductId(),
                reqDto.getQuantity()
        )));
    }

    // 4. PUT /cart/items
    @PutMapping("/items")
    public ResponseEntity<CartItemResDto> updateItemQuantity(@RequestBody UpdateItemQuantityReqDto reqDto) throws CartItemNotFoundException, CartNotFoundException {

        return ResponseEntity.ok(CartItemResDto.of(cartService.updateItemQuantity(
                userAuthentication.getUserId(),
                reqDto.getItemId(),
                reqDto.getQuantity()
        )));
    }

    // 5. POST /cart/checkout
    @PostMapping("/checkout")
    public ResponseEntity<ResponseMessage> checkout() throws JsonProcessingException, CartNotFoundException {
        cartService.checkout(userAuthentication.getUserId());
        return ResponseEntity.ok(new ResponseMessage(ResponseMessageType.SUCCESS, "Checkout successful"));
    }

    // 6. DELETE /cart
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> clearCart() throws CartNotFoundException {
        cartService.clearCart(userAuthentication.getUserId());
        return ResponseEntity.ok(new ResponseMessage(ResponseMessageType.SUCCESS, "Cart cleared successfully"));
    }
}
