package me.sridharpatil.ecom.userservice.authserver.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import  me.sridharpatil.ecom.userservice.models.User;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@JsonDeserialize
@Getter @Setter
public class JpaUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public JpaUserDetails() {
    }

    public JpaUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getHashedPassword();
        this.authorities = grantedAuthoritiesFromRoles(user.getRoles());
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    private Collection<GrantedAuthority> grantedAuthoritiesFromRoles(Set<Role> roles){
        return roles.stream()
                .map(role -> new JpaGrantedAuthority(role)).collect(Collectors.toList());
    }


    @JsonDeserialize
    @Getter @Setter
    public static class JpaGrantedAuthority implements GrantedAuthority{
        String authority;
        public JpaGrantedAuthority() {}
        public JpaGrantedAuthority(Role role) {
            this.authority = role.getName();
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
