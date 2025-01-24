package me.sridharpatil.ecom.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import me.sridharpatil.ecom.orderservice.services.dtos.OrderItemDto;

import java.util.List;

public interface OrderService {
    public Order createOrder(Long userId, List<OrderItemDto> orderItemDtoList) throws JsonProcessingException;
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws JsonProcessingException;
//    public Order getOrderById(Long orderId);
//    public List<Order> getOrdersByUserId(Long userId);
//    public void deleteOrder(Long orderId);
//    public List<Order> getAllOrders();
}
