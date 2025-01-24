package me.sridharpatil.ecom.orderservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.orderservice.consumers.dtos.OrderConfirmationReqDto;
import me.sridharpatil.ecom.orderservice.consumers.dtos.OrderCreationReqDto;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import me.sridharpatil.ecom.orderservice.services.OrderService;
import me.sridharpatil.ecom.orderservice.services.dtos.OrderItemDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class EventConsumer {

    OrderService orderService;
    ObjectMapper objectMapper;

    public EventConsumer(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "cart-service.checkout",
            groupId = "order-service.consumers"
    )
    public void consumeOrderCreationEvent(@Payload OrderCreationReqDto message) throws JsonProcessingException {


        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        List<OrderCreationReqDto.Item> items = message.getItems();
        for (OrderCreationReqDto.Item item : items){
            orderItemDtoList.add(
                    OrderItemDto.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .subTotal(item.getSubTotal())
                            .build()
            );
        }

        orderService.createOrder(message.getUserId(), orderItemDtoList);
    }


    @KafkaListener(
            topics = "payment-service.payment-success",
            groupId = "order-service.consumers"
    )
    public void consumeOrderConfirmationEvent(@Payload OrderConfirmationReqDto message) throws JsonProcessingException {
        orderService.updateOrderStatus(message.getOrderId(), OrderStatus.CONFIRMED);
    }

    @KafkaListener(
            topics = "payment-service.payment-failure",
            groupId = "order-service.consumers"
    )
    public void consumeOrderCancellationEvent(@Payload OrderConfirmationReqDto message) throws JsonProcessingException {
        orderService.updateOrderStatus(message.getOrderId(), OrderStatus.CANCELLED);
    }
}
