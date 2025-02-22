package me.sridharpatil.ecom.cartservice.controllers;

import me.sridharpatil.ecom.cartservice.controllers.dtos.CartResDto;
import me.sridharpatil.ecom.cartservice.controllers.dtos.CreateCartReqDto;
import me.sridharpatil.ecom.cartservice.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/cart")
public class CartInternalController {

    private final CartService cartService;

    public CartInternalController(CartService cartService) {
        this.cartService = cartService;
    }

    // 1. POST /private/cart
    @PostMapping()
    public ResponseEntity<CartResDto> createCart(@RequestBody CreateCartReqDto reqDto) {
        return ResponseEntity.ok(CartResDto.of(cartService.createCart(reqDto.getUserId())));
    }
}
