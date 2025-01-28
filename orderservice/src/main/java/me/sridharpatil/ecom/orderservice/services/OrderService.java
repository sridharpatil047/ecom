package me.sridharpatil.ecom.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import me.sridharpatil.ecom.orderservice.services.dtos.OrderItemDto;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<OrderItemDto> orderItemDtoList) throws JsonProcessingException;
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws JsonProcessingException;
    Order getOrderById(Long orderId);
    List<Order> getOrdersByUserId(Long userId);
    Order getOrderByUserIdAndStatus(Long userId, OrderStatus orderStatus);

//    public List<Order> getOrdersByUserId(Long userId);
//    public void deleteOrder(Long orderId);
//    public List<Order> getAllOrders();
}
