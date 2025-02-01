package me.sridharpatil.ecom.orderservice.controllers;

import me.sridharpatil.ecom.orderservice.controllers.dtos.GetOrderResDto;
import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import me.sridharpatil.ecom.orderservice.services.OrderService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GetOrderResDto> getOrder(
            @RequestParam(value = "orderId", required = false) Long orderId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "status", required = false) String status
    ) {

        if (orderId != null) {
            Order order =  orderService.getOrderById(orderId);
            return ResponseEntity.ok().body(GetOrderResDto.of(order));
        } else if (userId != null && status != null) {
            Order order = orderService.getOrderByUserIdAndStatus(
                    userId,
                    OrderStatus.valueOf(status.toUpperCase())
            );
            return ResponseEntity.ok().body(GetOrderResDto.of(order));
        }
        return ResponseEntity.badRequest().build();
    }

}
