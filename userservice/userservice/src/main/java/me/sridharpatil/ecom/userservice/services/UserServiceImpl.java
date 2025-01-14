package me.sridharpatil.ecom.userservice.services;

import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExists;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User signUp(String name, String email, String password) throws UserAlreadyExists {

        // Check if user already exists
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new UserAlreadyExists("User with email " + email + " already exists");
        }

        // Since user does not exist, create a new user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // Hash the password and set it
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        // Save the user
        return userRepository.save(user);
    }
}
