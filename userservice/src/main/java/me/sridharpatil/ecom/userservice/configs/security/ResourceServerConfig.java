package me.sridharpatil.ecom.userservice.configs.security;

import me.sridharpatil.ecom.userservice.configs.security.customizers.RequestMatchers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class ResourceServerConfig {

    private final RequestMatchers requestMatchers;
    private final Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> oauth2ResourceServerConfigurer;


    public ResourceServerConfig(RequestMatchers requestMatchers, Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> oauth2ResourceServerConfigurer) {
        this.requestMatchers = requestMatchers;
        this.oauth2ResourceServerConfigurer = oauth2ResourceServerConfigurer;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requestMatchers)
                .oauth2ResourceServer(oauth2ResourceServerConfigurer)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
