package me.sridharpatil.ecom.orderservice.controllers;

import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.services.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}/users")
    public Long getUserIdByOrderById(@PathVariable("orderId") long orderId) {
        Order order = orderService.getOrderById(orderId);
        return order.getUserId();
    }

}
