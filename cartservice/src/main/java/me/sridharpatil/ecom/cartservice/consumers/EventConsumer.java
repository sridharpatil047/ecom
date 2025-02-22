package me.sridharpatil.ecom.cartservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.cartservice.consumers.dtos.UserCreatedReqDto;
import me.sridharpatil.ecom.cartservice.services.CartService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class EventConsumer {

    CartService cartService;
    ObjectMapper objectMapper;

    public EventConsumer(CartService cartService, ObjectMapper objectMapper) {
        this.cartService = cartService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "user-service.user-created",
            groupId = "cart-service.consumers"
    )
    public void consumeUserCreatedEvent(@Payload String message) throws JsonProcessingException {
        log.debug("Entered consumeCartCheckedOutEvent");

        UserCreatedReqDto userCreatedReqDto = objectMapper.readValue(message, UserCreatedReqDto.class);

        cartService.createCart(userCreatedReqDto.getUserId());
    }
}
