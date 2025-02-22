package me.sridharpatil.ecom.cartservice.contexts.authentication;

import org.springframework.security.core.Authentication;

public interface UserAuthentication<T> extends Authentication {
    T getUserId();
}
