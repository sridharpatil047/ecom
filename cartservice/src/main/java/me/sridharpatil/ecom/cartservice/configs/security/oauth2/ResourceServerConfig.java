package me.sridharpatil.ecom.cartservice.configs.security.oauth2;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableMethodSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->authorizeRequests
                        .requestMatchers("/private/**").permitAll()
                        // /cart
                        .requestMatchers(HttpMethod.GET,"/cart").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/cart").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/cart").hasRole("USER")

                        // /cart/items
                        .requestMatchers(HttpMethod.POST,"/cart/items").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/cart/items").hasRole("USER")

                        // /cart/checkout
                        .requestMatchers(HttpMethod.POST,"/cart/checkout").hasRole("USER")
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
