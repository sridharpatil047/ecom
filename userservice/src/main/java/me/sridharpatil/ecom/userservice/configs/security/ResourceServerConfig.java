package me.sridharpatil.ecom.userservice.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->authorizeRequests
                        // permit all
                        .requestMatchers("/private/**").permitAll() // Private endpoints for inter-service communication
                        .requestMatchers(HttpMethod.POST,"/users").permitAll() // Create User
                        .requestMatchers("/users/{id}/password-resets/**").permitAll()

                        // /users
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}/roles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/users/{id}/shipping-addresses").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/users/{id}/password").hasRole("USER")

                        // /roles
                        .requestMatchers("/roles/**").hasRole("ADMIN")

                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())))

                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");


        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
