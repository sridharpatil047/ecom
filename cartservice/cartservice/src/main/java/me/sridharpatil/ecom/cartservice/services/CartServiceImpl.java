package me.sridharpatil.ecom.cartservice.services;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;
import me.sridharpatil.ecom.cartservice.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CartServiceImpl implements CartService{
    CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart(Long userId) {
        log.debug("Creating cart for user: {}", userId);
        Cart cart = new Cart();
        cart.setUserId(userId);

        log.info("Cart created for user: {}", userId);
        return cartRepository.save(cart);
    }

    @Override
    public void addItemToCart(Long userId, Long productId, double price, int quantity) {
        log.debug("Adding item to cart for user: {}, product: {}, price: {}, quantity: {}", userId, productId, price, quantity);
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price);
        cartItem.setSubTotal(price * quantity);

        log.debug("Checking if cart exists for user: {}", userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        log.info("Adding item to cart for user: {}, product: {}", userId, productId);
        Cart cart = optionalCart.get();
        cart.getItems().add(cartItem);

        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long userId, Long productId, int quantity) {
        log.debug("Updating item quantity for user: {}, product: {}, quantity: {}", userId, productId, quantity);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        Cart cart = optionalCart.get();
        Optional<CartItem> optionalCartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
        if (optionalCartItem.isEmpty()){
            throw new RuntimeException("Item not found in cart for user: " + userId + ", product: " + productId);
        }

        log.info("Updating item quantity for user: {}, product: {}", userId, productId);
        CartItem cartItem = optionalCartItem.get();
        cartItem.setQuantity(quantity);
        cartItem.setSubTotal(cartItem.getPrice() * quantity);

        cartRepository.save(cart);
    }
}
