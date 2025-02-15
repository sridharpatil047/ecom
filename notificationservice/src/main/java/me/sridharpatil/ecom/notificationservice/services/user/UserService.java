package me.sridharpatil.ecom.notificationservice.services.user;

import me.sridharpatil.ecom.notificationservice.utils.URLBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserById(Long userId) {

        String url = URLBuilder.getBuilder()
                .setBaseUrl("http://userservice")
                .setPath("/private/users")
                .setPathParam(userId.toString())
                .build();

        return restTemplate.getForObject(url, User.class);
    }
}
