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

        Map<String, String> queryParams = Map.of("orderId", orderId.toString());
        String url = URLBuilder.getBuilder()
                .setBaseUrl("http://localhost:8085")
                .setPath("/orders")
                .setQueryParams(queryParams)
                .build();

        return restTemplate.getForObject(url, Order.class);
    }
}
