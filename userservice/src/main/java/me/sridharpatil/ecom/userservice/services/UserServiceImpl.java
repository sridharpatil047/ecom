package me.sridharpatil.ecom.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.Role;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.repositories.UserRepository;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import me.sridharpatil.ecom.userservice.services.notifications.NotificationSenderContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    RoleService roleService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    NotificationSenderContext notificationSenderContext;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, NotificationSenderContext notificationSenderContext) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.notificationSenderContext = notificationSenderContext;
    }

    @Override
    public User signUp(String name, String email, String password) throws UserAlreadyExistsException, JsonProcessingException {

        // Check if user already exists
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        // Since user does not exist, create a new user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // Hash the password and set it
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        // Save the user
        User savedUser = userRepository.save(user);
        log.info("User saved successfully");

        // Send notification
        log.info("Sending notification");
        notificationSenderContext.sendNotification(savedUser, Event.USER_CREATED);


        log.info("User signed up successfully");
        return savedUser;
    }


    @Override
    public void updateUser(Long userId, UserDto userDto) throws RoleNotFoundException, UserNotFoundException {

        // Check if user exists
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }

        // Since user exists, update the user
        User user = userRepository.findById(userId).get();

        // update the user with new roles
        if (userDto.getRoles() != null) {
            Set<Role> roleList = user.getRoles();
            for (String role : userDto.getRoles()) {
                roleList.add(roleService.getRoleByName(role));
            }
            user.setRoles(roleList);
        }

        // update the user with new password
        if (userDto.getNewPassword() != null) {
            user.setHashedPassword(bCryptPasswordEncoder.encode(userDto.getNewPassword()));
        }

        // Save the user
        userRepository.save(user);
    }
}
