package me.sridharpatil.ecom.orderservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import me.sridharpatil.ecom.orderservice.exceptions.ShippingAddressNotFoundException;
import me.sridharpatil.ecom.orderservice.models.ShippingAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class UserService {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    HttpServletRequest request;

    public UserService(RestTemplate restTemplate, ObjectMapper objectMapper, HttpServletRequest request) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ShippingAddress getActiveShippingAddress(Long userId) throws ShippingAddressNotFoundException {
        ShippingAddress[] shippingAddresses
                = restTemplate.getForObject("http://localhost:8082/private/users/"  + userId +"/shipping-addresses", ShippingAddress[].class);
        for (ShippingAddress shippingAddress : shippingAddresses) {
            if (shippingAddress.isActive()) {
                return shippingAddress;
            }
        }

        throw new ShippingAddressNotFoundException("No active shipping address found for user: " + userId);
    }

    public static class URL {
        public static final String BASE_URL = "http://localhost:8082";
        public static final String ENDPOINT = "/users";
    }
}
