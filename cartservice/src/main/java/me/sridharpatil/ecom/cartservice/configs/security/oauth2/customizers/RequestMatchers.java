package me.sridharpatil.ecom.cartservice.configs.security.oauth2.customizers;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RequestMatchers implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requestMatcherRegistry) {

        requestMatcherRegistry
//                    .anyRequest().permitAll()
                .requestMatchers("/products/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
