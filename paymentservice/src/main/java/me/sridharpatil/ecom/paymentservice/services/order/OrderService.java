package me.sridharpatil.ecom.paymentservice.services.order;

import me.sridharpatil.ecom.paymentservice.utils.URLBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Long getUserIdByOrderId(Long orderId) {
        String url = URLBuilder.getBuilder()
                .setBaseUrl("lb://orderservice")
                .setPath("/private/orders/" + orderId + "/users")
                .build();
        return restTemplate.getForObject(url, Long.class);
    }

}
