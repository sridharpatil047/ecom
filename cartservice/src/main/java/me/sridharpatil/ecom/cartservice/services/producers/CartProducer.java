package me.sridharpatil.ecom.cartservice.services.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import me.sridharpatil.ecom.cartservice.models.Cart;
import me.sridharpatil.ecom.cartservice.models.CartItem;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartProducer {

    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    public CartProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void checkedOutEvent(Cart cart) throws JsonProcessingException {
        CheckedOutEventResDto checkedOutEventResDto = CheckedOutEventResDto.of(cart);
        String message = objectMapper.writeValueAsString(checkedOutEventResDto);
        kafkaTemplate.send(
                "cart-service.checked-out",
                checkedOutEventResDto.getUserId().toString(),
                message
        );
    }

}
