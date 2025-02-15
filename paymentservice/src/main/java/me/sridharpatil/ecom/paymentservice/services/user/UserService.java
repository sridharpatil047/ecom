package me.sridharpatil.ecom.paymentservice.services.user;

import me.sridharpatil.ecom.paymentservice.utils.URLBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUser(Long userId) {
        String url = URLBuilder.getBuilder()
                .setBaseUrl("lb://userservice")
                .setPath("/private/users")
                .setPathParam(userId.toString())
                .build();
        return restTemplate.getForObject(url, UserDto.class);
    }
    public String getUserName(Long userId) { return getUser(userId).getName(); }
    public String getUserEmail(Long userId) { return getUser(userId).getEmail(); }

}
