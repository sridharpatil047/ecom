package me.sridharpatil.ecom.orderservice.controllers;

import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import me.sridharpatil.ecom.orderservice.services.OrderService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public Order getOrder(
            @RequestParam("userId") Long userId,
            @RequestParam("status") String status
    ) {

        return orderService
                .getOrderByUserIdAndStatus(
                        userId,
                        OrderStatus.valueOf(status.toUpperCase())
                );
    }

}
