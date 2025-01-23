package me.sridharpatil.ecom.userservice.authserver.services;

import me.sridharpatil.ecom.userservice.authserver.models.JpaUserDetails;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findUserByEmail(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("");

        User user = optionalUser.get();

        return new JpaUserDetails(user);
    }
}
