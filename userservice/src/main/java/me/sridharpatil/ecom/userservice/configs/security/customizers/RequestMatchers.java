package me.sridharpatil.ecom.userservice.configs.security.customizers;

import org.springframework.http.HttpMethod;
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
                .requestMatchers("/users/sign-up", "/oauth2/authorize", "/oauth2/token").permitAll()
                .requestMatchers("/roles/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/users/{id}/roles").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
