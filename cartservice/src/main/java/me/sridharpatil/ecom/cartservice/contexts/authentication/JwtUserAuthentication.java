package me.sridharpatil.ecom.cartservice.contexts.authentication;

import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Setter
public class JwtUserAuthentication implements UserAuthentication<Long> {

    JwtAuthenticationToken jwtAuthenticationToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        setJwtAuthenticationToken((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        return jwtAuthenticationToken.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        setJwtAuthenticationToken((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        return jwtAuthenticationToken.getCredentials();
    }

    @Override
    public Object getDetails() {
        setJwtAuthenticationToken((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        return jwtAuthenticationToken.getDetails();
    }

    @Override
    public Jwt getPrincipal() {
        setJwtAuthenticationToken((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        return (Jwt) jwtAuthenticationToken.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        setJwtAuthenticationToken((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        return jwtAuthenticationToken.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        setJwtAuthenticationToken((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        jwtAuthenticationToken.setAuthenticated(isAuthenticated);
    }

    @Override
    public String getName() {
        setJwtAuthenticationToken((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        return jwtAuthenticationToken.getName();
    }

    @Override
    public Long getUserId() {
        return (Long) getPrincipal().getClaims().get("user_id");
    }
}
