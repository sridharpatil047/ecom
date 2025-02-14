package me.sridharpatil.ecom.apigateway.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIGatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**", "/roles/**", "/oauth2/**")
                        .uri("lb://userservice"))

                .route("product-service", r -> r.path("/products/**", "/categories/**")
                        .uri("lb://productservice"))

                .route("cart-service", r -> r.path("/cart/**")
                        .uri("lb://cartservice"))

                .route("order-service", r -> r.path("/orders/**")
                        .uri("lb://orderservice"))

                .route("payment-service", r -> r.path("/payments/**")
                        .uri("lb://paymentservice"))

                .build();
    }
}
