package me.sridharpatil.ecom.userservice.services;

import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExists;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.Role;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.repositories.UserRepository;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    RoleService roleService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
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

    @Override
    public User updateUser(Long userId, UserDto userDto) throws RoleNotFoundException, UserNotFoundException {

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
        return userRepository.save(user);
    }
}
