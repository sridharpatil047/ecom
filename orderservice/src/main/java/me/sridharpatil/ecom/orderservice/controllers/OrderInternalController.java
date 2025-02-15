package me.sridharpatil.ecom.orderservice.controllers;

import me.sridharpatil.ecom.orderservice.controllers.dtos.GetOrderResDto;
import me.sridharpatil.ecom.orderservice.services.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/private/orders")
public class OrderInternalController {
    private final OrderService orderService;

    public OrderInternalController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public GetOrderResDto getOrderById(@PathVariable("id") Long orderId) {
        return GetOrderResDto.of(orderService.getOrderById(orderId));
    }

    @GetMapping("/{id}/users")
    public Long getUserIdByOrderById(@PathVariable("id") long orderId) {
        return orderService.getOrderById(orderId).getUserId();
    }

}
