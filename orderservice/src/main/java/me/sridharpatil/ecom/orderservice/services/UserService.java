package me.sridharpatil.ecom.orderservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.orderservice.exceptions.ShippingAddressNotFoundException;
import me.sridharpatil.ecom.orderservice.models.ShippingAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;

    public UserService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ShippingAddress getActiveShippingAddress(Long userId) throws ShippingAddressNotFoundException {

        ShippingAddress[] shippingAddresses
                = restTemplate.getForObject("http://localhost:8082/users/"  + userId +"/shipping-addresses", ShippingAddress[].class);

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
