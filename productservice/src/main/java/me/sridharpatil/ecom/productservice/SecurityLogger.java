package me.sridharpatil.ecom.productservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SecurityLogger implements CommandLineRunner {
    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Override
    public void run(String... args) {
        System.out.println("Security Username: " + username);
        System.out.println("Security Password: " + password);
    }
}
