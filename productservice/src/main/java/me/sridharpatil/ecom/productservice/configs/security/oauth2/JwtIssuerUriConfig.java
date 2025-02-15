package me.sridharpatil.ecom.productservice.configs.security.oauth2;

import me.sridharpatil.ecom.productservice.properties.ApplicationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
        String serviceId = instances.get(0).getServiceId();
        return serviceId  + applicationProperties.jwtIssuerEndpoint;
    }
}
