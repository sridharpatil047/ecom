package me.sridharpatil.ecom.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.orderservice.exceptions.ShippingAddressNotFoundException;
import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import me.sridharpatil.ecom.orderservice.models.ShippingAddress;
import me.sridharpatil.ecom.orderservice.repositories.OrderRepository;
import me.sridharpatil.ecom.orderservice.services.dtos.OrderItemDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    OrderRepository orderRepository;
    UserService userService;
    KafkaTemplate<Long, String> kafkaTemplate;
    ObjectMapper objectMapper;


    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Order createOrder(Long userId, List<OrderItemDto> orderItemDtoList) throws JsonProcessingException {

        ShippingAddress shippingAddress = null;
        try {
            shippingAddress = userService.getActiveShippingAddress(userId);
        } catch (ShippingAddressNotFoundException e) {
            kafkaTemplate.send(
                    "order-service.order-create-failed",
                    userId,
                    "Order creation failed for user: " + userId + ". Reason: " + e.getMessage()
            );
            return null;
        }

        Order order = Order.builder()
                .userId(userId)
                .totalPrice(
                        orderItemDtoList.stream()
                                .mapToDouble(OrderItemDto::getSubTotal)
                                .sum()
                )
                .status(OrderStatus.PENDING)
                .shippingAddress(shippingAddress)
                .orderItems(
                        orderItemDtoList.stream()
                                .map(OrderItemDto::toEntity)
                                .collect(Collectors.toList())
                )
                .build();

        order = orderRepository.save(order);

        kafkaTemplate.send(
                "order-service.order-created",
                order.getId(),
                objectMapper.writeValueAsString(order)
        );

        return order;
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws JsonProcessingException {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null){return null;}

        order.setStatus(orderStatus);
        order = orderRepository.save(order);

        if (order.getStatus().equals(OrderStatus.CONFIRMED)){
            kafkaTemplate.send(
                    "order-service.order-confirmed",
                    order.getId(),
                    objectMapper.writeValueAsString(order)
            );
        } else if (order.getStatus().equals(OrderStatus.CANCELLED)){
            kafkaTemplate.send(
                    "order-service.order-cancelled",
                    order.getId(),
                    objectMapper.writeValueAsString(order)
            );
        }

        return order;
    }
}
