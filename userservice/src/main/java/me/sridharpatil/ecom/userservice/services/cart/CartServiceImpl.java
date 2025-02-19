package me.sridharpatil.ecom.userservice.services.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CartServiceImpl implements CartService{

    ObjectMapper objectMapper;
    KafkaTemplate<String, String> kafkaTemplate;

    public CartServiceImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Cart createCart(Long userId) throws JsonProcessingException {
        log.debug("Entering createCart");
        Cart cart = new Cart();
        cart.setUserId(userId);

        String message = objectMapper.writeValueAsString(cart);
        kafkaTemplate.send(
                "user-service.user-created",
                cart.getUserId().toString(),
                message);

        log.debug("Exit createCart");
        log.info("Message queued to Kafka topic. {}", message);
        return cart;
    }
}
