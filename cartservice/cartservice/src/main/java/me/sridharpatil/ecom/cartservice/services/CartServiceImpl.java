package me.sridharpatil.ecom.cartservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;
import me.sridharpatil.ecom.cartservice.repositories.CartItemRepository;
import me.sridharpatil.ecom.cartservice.repositories.CartRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Log4j2
@Service
public class CartServiceImpl implements CartService{
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ObjectMapper objectMapper;
    KafkaTemplate<String, String> kafkaTemplate;


    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Cart createCart(Long userId) {

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()){
            log.error("Cart already exists for user: {}, skipping cart creation", userId);
            return optionalCart.get();
        }

        log.debug("Creating cart for user: {}", userId);
        Cart cart = new Cart();
        cart.setUserId(userId);

        log.info("Cart created for user: {}", userId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long userId) {
        log.debug("Getting cart for user: {}", userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        log.info("Cart found for user: {}", userId);
        return optionalCart.get();
    }

    @Override
    public CartItem addItemToCart(Long userId, Long productId, double price, int quantity) {
        log.debug("Adding item to cart for user: {}, product: {}, price: {}, quantity: {}", userId, productId, price, quantity);
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price);
        cartItem.setSubTotal(price * quantity);

        cartItemRepository.save(cartItem);

        log.debug("Checking if cart exists for user: {}", userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        log.info("Adding item to cart for user: {}, product: {}", userId, productId);
        Cart cart = optionalCart.get();
        cart.getItems().add(cartItem);
        double totalPrice = cart.getItems()
                .stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);
        return cartItem;
    }

    @Override
    public CartItem updateItemQuantity(Long userId, Long itemId, int quantity) {

        log.debug("Updating item quantity for user: {}, item: {}, quantity: {}", userId, itemId, quantity);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        log.debug("Searching for item in cart for user: {}, item: {}", userId, itemId);
        Cart cart = optionalCart.get();
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        log.debug("Update item quantity for user: {}, item: {}, quantity: {}", userId, itemId, quantity);
        cartItem.setQuantity(quantity);
        cartItem.setSubTotal(cartItem.getPrice() * quantity);

        cartItemRepository.save(cartItem);

        cart.setTotalPrice(cart.getItems().stream().mapToDouble(CartItem::getSubTotal).sum());

        cartRepository.save(cart);

        log.info("Item quantity updated for user: {}, item: {}", userId, itemId);
        return cartItem;
    }

    @Override
    public void checkout(Long userId) throws JsonProcessingException {

        log.debug("Checking out cart for user: {}", userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        log.debug("Updating total price for cart for user: {}", userId);
        Cart cart = optionalCart.get();
        double totalPrice = cart.getItems().stream().mapToDouble(CartItem::getSubTotal).sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        log.info("Producing message to kafka topic: cart-service.checkout");
        String message = objectMapper.writeValueAsString(cart);
        kafkaTemplate.send("cart-service.checkout", message);

        log.debug("Clearing cart for user: {}", userId);
        clearCart(userId);
    }

    @Override
    public void clearCart(Long userId) {

        log.debug("Clearing cart for user: {}", userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            log.error("Cart not found for user: {}", userId);
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        log.info("Clearing cart for user: {}", userId);
        Cart cart = optionalCart.get();
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);
        log.debug("Cart cleared for user: {}", userId);
    }
}
