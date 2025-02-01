package me.sridharpatil.ecom.notificationservice.services.user;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserById(Long userId) {

        return restTemplate.getForObject("http://localhost:8082/users/{userId}", User.class, userId);

//        User user = new User();
//        user.setUserId(userId);
//        user.setName("John Doe");
//        user.setEmail("sridharpatil047@gmail.com");
//        return user;
    }
}
