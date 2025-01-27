package me.sridharpatil.ecom.cartservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.cartservice.exceptions.CartItemNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.CartNotFoundException;
import me.sridharpatil.ecom.cartservice.exceptions.ProductAlreadyExistsException;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;
import me.sridharpatil.ecom.cartservice.repositories.CartItemRepository;
import me.sridharpatil.ecom.cartservice.repositories.CartRepository;
import me.sridharpatil.ecom.cartservice.services.producers.CartProducer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CartServiceImpl implements CartService{
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ObjectMapper objectMapper;
    KafkaTemplate<String, String> kafkaTemplate;
    RedisTemplate<String, Object> redisTemplate;
    CartProducer cartProducer;


    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate, RedisTemplate<String, Object> redisTemplate, CartProducer cartProducer) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.redisTemplate = redisTemplate;
        this.cartProducer = cartProducer;
    }

    @Override
    public Cart createCart(Long userId) {

        log.debug("Checking if cart already exists in redis for user: {}", userId);
        Cart redisCart = (Cart) redisTemplate.opsForHash().get("cart", userId);
        if (redisCart != null){
            log.error("Cart already exists in redis for user: {}, skipping cart creation", userId);
            return redisCart;
        }

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()){
            log.error("Cart already exists for user: {}, skipping cart creation", userId);
            return optionalCart.get();
        }

        log.debug("Creating cart for user: {}", userId);
        Cart cart = new Cart();
        cart.setUserId(userId);

        log.info("Cart created for user: {} and saving to database", userId);
        cart = cartRepository.save(cart);

        log.debug("Saving cart to redis for user: {}", userId);
        redisTemplate.opsForHash().put("cart", userId, cart);

        return cart;
    }

    @Override
    public Cart getCart(Long userId) throws CartNotFoundException {
        log.debug("Getting cart for user: {}", userId);

        log.debug("Checking if cart exists in redis for user: {}", userId);
        Cart redisCart = (Cart) redisTemplate.opsForHash().get("cart", userId);
        if (redisCart != null){
            log.info("Cart found in redis for user: {}", userId);
            return redisCart;
        }

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new CartNotFoundException("Cart not found for user: " + userId);
        }

        log.info("Cart found for user: {}", userId);
        return optionalCart.get();
    }

    @Override
    public CartItem addItemToCart(Long userId, Long productId, double price, int quantity) throws ProductAlreadyExistsException, CartNotFoundException {

        // Check if product already exists in cart
        log.debug("Checking if product already exists in cart for user: {}, product: {}", userId, productId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new CartNotFoundException("Cart not found for user: " + userId);
        }
        Cart cart = optionalCart.get();
        CartItem cartItem1 = cart.getItems()
                .stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem1 != null){
            log.error("Product already exists in cart for user: {}, product: {}", userId, productId);
            throw new ProductAlreadyExistsException("Product " + productId + " already exists in cart");
        }

        log.debug("Adding item to cart for user: {}, product: {}, price: {}, quantity: {}", userId, productId, price, quantity);
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price);
        cartItem.setSubTotal(price * quantity);

        cartItemRepository.save(cartItem);

        log.debug("Saving cart item to redis for user: {}, product: {}", userId, productId);
        redisTemplate.opsForHash().put("cart-item", cartItem.getId(), cartItem);

        log.info("Adding item to cart for user: {}, product: {}", userId, productId);
        cart.getItems().add(cartItem);
        double totalPrice = cart.getItems()
                .stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        log.debug("Saving cart to redis for user: {}", userId);
        redisTemplate.opsForHash().put("cart", userId, cart);

        return cartItem;
    }

    @Override
    public CartItem updateItemQuantity(Long userId, Long itemId, int quantity) throws CartNotFoundException, CartItemNotFoundException {

        log.debug("Updating item quantity for user: {}, item: {}, quantity: {}", userId, itemId, quantity);

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new CartNotFoundException("Cart not found for user: " + userId);
        }

        log.debug("Searching for item in cart for user: {}, item: {}", userId, itemId);
        Cart cart = optionalCart.get();
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("Item not found in cart"));

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
    public void checkout(Long userId) throws JsonProcessingException, CartNotFoundException {

        log.debug("Checking out cart for user: {}", userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            throw new CartNotFoundException("Cart not found for user: " + userId);
        }

        log.debug("Updating total price for cart for user: {}", userId);
        Cart cart = optionalCart.get();
        double totalPrice = cart.getItems().stream().mapToDouble(CartItem::getSubTotal).sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        log.info("Producing message to kafka topic: cart-service.checkout");
        cartProducer.checkedOutEvent(cart);

        log.debug("Clearing cart for user: {}", userId);
        clearCart(userId);
    }

    @Override
    public void clearCart(Long userId) throws CartNotFoundException {

        log.debug("Clearing cart for user: {}", userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()){
            log.error("Cart not found for user: {}", userId);
            throw new CartNotFoundException("Cart not found for user: " + userId);
        }

        log.info("Clearing cart for user: {}", userId);
        Cart cart = optionalCart.get();
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
        log.debug("Cart cleared for user: {}", userId);
    }
}
