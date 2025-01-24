package me.sridharpatil.ecom.orderservice.services;

import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import me.sridharpatil.ecom.orderservice.repositories.OrderRepository;
import me.sridharpatil.ecom.orderservice.services.dtos.OrderItemDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    OrderRepository orderRepository;
    UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public Order createOrder(Long userId, List<OrderItemDto> orderItemDtoList) {


        Order order = Order.builder()
                .userId(userId)
                .totalPrice(
                        orderItemDtoList.stream()
                                .mapToDouble(OrderItemDto::getSubTotal)
                                .sum()
                )
                .status(OrderStatus.PENDING)
                .shippingAddress(userService.getActiveShippingAddress(userId))
                .orderItems(
                        orderItemDtoList.stream()
                                .map(OrderItemDto::toEntity)
                                .collect(Collectors.toList())
                )
                .build();

        order = orderRepository.save(order);

        return order;
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null){
            order.setStatus(orderStatus);
            order = orderRepository.save(order);
            return order;
        }

        return null;
    }
}
