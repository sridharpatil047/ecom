package me.sridharpatil.ecom.paymentservice.services.user;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUser(Long userId) {
        return restTemplate.getForObject("http://localhost:8082/users/" + userId, UserDto.class);
    }
    public String getUserName(Long userId) { return getUser(userId).getName(); }
    public String getUserEmail(Long userId) { return getUser(userId).getEmail(); }

}
