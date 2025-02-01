package me.sridharpatil.ecom.notificationservice.services.order;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order getOrderById(Long orderId) {
        return restTemplate.getForObject("http://localhost:8085/orders?orderId=" + orderId, Order.class);
    }
}
