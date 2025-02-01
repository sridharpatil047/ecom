package me.sridharpatil.ecom.orderservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.orderservice.consumers.dtos.OrderCancellationReqDto;
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
            topics = "cart-service.checked-out",
            groupId = "order-service.consumers"
    )
    public void consumeCartCheckedOutEvent(@Payload String message) throws JsonProcessingException {

        log.debug("Entered consumeCartCheckedOutEvent");

        OrderCreationReqDto orderCreationReqDto = objectMapper.readValue(message, OrderCreationReqDto.class);


        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        List<OrderCreationReqDto.Item> items = orderCreationReqDto.getItems();
        for (OrderCreationReqDto.Item item : items){
            orderItemDtoList.add(
                    OrderItemDto.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .subTotal(item.getSubTotal())
                            .build()
            );
        }

        orderService.createOrder(orderCreationReqDto.getUserId(), orderItemDtoList);
    }


    @KafkaListener(
            topics = "payment-service.payment-success",
            groupId = "order-service.consumers"
    )
    public void consumeOrderConfirmationEvent(@Payload String message) throws JsonProcessingException {

        log.debug("Entered consumeOrderConfirmationEvent");

        OrderConfirmationReqDto orderConfirmationReqDto =  objectMapper.readValue(message, OrderConfirmationReqDto.class);

        orderService.updateOrderStatus(orderConfirmationReqDto.getOrderId(), OrderStatus.CONFIRMED);
    }

    @KafkaListener(
            topics = "payment-service.payment-failed",
            groupId = "order-service.consumers"
    )
    public void consumeOrderCancellationEvent(@Payload String message) throws JsonProcessingException {

        log.debug("Entered consumeOrderCancellationEvent");

        OrderCancellationReqDto orderCancellationReqDto =  objectMapper.readValue(message, OrderCancellationReqDto.class);

        orderService.updateOrderStatus(orderCancellationReqDto.getOrderId(), OrderStatus.CANCELLED);
    }
}
