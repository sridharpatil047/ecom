package me.sridharpatil.ecom.orderservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.orderservice.models.ShippingAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;

    public UserService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ShippingAddress getActiveShippingAddress(Long userId) {

        ShippingAddress shippingAddress
                = restTemplate.getForObject(URL.BASE_URL + "/" + URL.ENDPOINT + "/" + userId, ShippingAddress.class);

        return shippingAddress;
    }

    public static class URL {
        public static final String BASE_URL = "http://localhost:8080";
        public static final String ENDPOINT = "/users";
    }
}
