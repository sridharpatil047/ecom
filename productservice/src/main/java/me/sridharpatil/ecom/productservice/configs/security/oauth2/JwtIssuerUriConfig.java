package me.sridharpatil.ecom.productservice.configs.security.oauth2;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.properties.ApplicationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.List;

@Log4j2
@Configuration
public class JwtIssuerUriConfig {
    private final ApplicationProperties applicationProperties;
    private final DiscoveryClient discoveryClient;

    public JwtIssuerUriConfig(ApplicationProperties applicationProperties, DiscoveryClient discoveryClient) {
        this.applicationProperties = applicationProperties;
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public String jwtIssuerUri() {
        List<ServiceInstance> instances = discoveryClient.getInstances(applicationProperties.jwtIssuerService);
        if (instances.isEmpty()) {
            throw new IllegalStateException("Auth service not found in Eureka");
        }


        log.info("JWT Issuer URI: {}", instances.getFirst().getUri());
        URI serviceId = instances.getFirst().getUri();
        return serviceId  + applicationProperties.jwtIssuerEndpoint;
    }
}
