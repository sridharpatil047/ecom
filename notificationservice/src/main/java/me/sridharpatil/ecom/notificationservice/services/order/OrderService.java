package me.sridharpatil.ecom.notificationservice.services.order;

import me.sridharpatil.ecom.notificationservice.utils.URLBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OrderService {

    RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order getOrderById(Long orderId) {

        String url = URLBuilder.getBuilder()
                .setBaseUrl("http://orderservice")
                .setPath("/private/orders")
                .setPathParam(orderId.toString())
                .build();

        return restTemplate.getForObject(url, Order.class);
    }
}
