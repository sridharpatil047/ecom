package me.sridharpatil.ecom.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.ShippingAddressNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.OneTimePassword;
import me.sridharpatil.ecom.userservice.models.Role;
import me.sridharpatil.ecom.userservice.models.ShippingAddress;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.repositories.OneTimePasswordRepository;
import me.sridharpatil.ecom.userservice.repositories.ShippingAddressRepository;
import me.sridharpatil.ecom.userservice.repositories.UserRepository;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import me.sridharpatil.ecom.userservice.services.notification.NotificationService;
import me.sridharpatil.ecom.userservice.services.notification.PasswordResetNotification;
import me.sridharpatil.ecom.userservice.services.notification.UserCreatedNotification;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Log4j2
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    ShippingAddressRepository shippingAddressRepository;
    RoleService roleService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    NotificationService notificationService;
    OneTimePasswordRepository oneTimePasswordRepository;

    public UserServiceImpl(UserRepository userRepository, ShippingAddressRepository shippingAddressRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, NotificationService notificationService, OneTimePasswordRepository oneTimePasswordRepository) {
        this.userRepository = userRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.notificationService = notificationService;
        this.oneTimePasswordRepository = oneTimePasswordRepository;
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

        // Create a Cart
        // TODO

        // Send notification
        log.info("Sending notification");
        UserCreatedNotification userCreatedNotification = UserCreatedNotification.getBuilder()
                .setUserId(savedUser.getId())
                .build();
        notificationService.send(userCreatedNotification);
//        notificationSenderContext.sendNotification(savedUser, Event.USER_CREATED);


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
        if (userDto.getNewPassword() != null && userDto.getOldPassword() != null) {
            if (!bCryptPasswordEncoder.matches(userDto.getOldPassword(), user.getHashedPassword())){
                log.error("Old password does not match");
                throw new RuntimeException("Old password does not match");
            }
            user.setHashedPassword(bCryptPasswordEncoder.encode(userDto.getNewPassword()));
        }

        // Save the user
        userRepository.save(user);
    }

    @Override
    public List<ShippingAddress> getShippingAddresses(Long userId) throws ShippingAddressNotFoundException {
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAllByUserId(userId);
        if (shippingAddresses.isEmpty()) {
            log.error("No shipping addresses found for user: {}", userId);
            throw new ShippingAddressNotFoundException("No shipping addresses found for user: " + userId);
        }

        return shippingAddresses;
    }

    @Override
    public User getUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    @Override
    public void addShippingAddress(Long id, ShippingAddress shippingAddress) {
        shippingAddress.setUser(userRepository.findById(id).get());
        shippingAddress.setActive(true);
        shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public void createOTP(Long userID) {

    }

    @Override
    public void resetPassword(Long userId) throws UserNotFoundException, JsonProcessingException {
        // 1. Check if user exists
        log.debug("Checking if user exists");
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        // 2. If user exists, generate 4 digit Random Integer
        log.debug("Generating OTP for user: {}", userId);
        User user = optionalUser.get();
        Integer otp = RandomUtils.nextInt(9999);

        // 3. Save to DB
        log.debug("Saving otp to DB");
        OneTimePassword oneTimePassword = OneTimePassword.builder()
                .value(otp)
                .expiryDateTime(LocalDateTime.now().plusMinutes(5)) // OTP expires in 5 min
                .user(user)
                .build();
        oneTimePasswordRepository.save(oneTimePassword);

        // 4. Send to Kafka
        log.debug("Sending notification");
        PasswordResetNotification notification = PasswordResetNotification.getBuilder()
                .setUserId(user.getId())
                .setOtp(otp)
                .build();
        notificationService.send(notification);
    }

    @Override
    public User confirmPasswordReset(Integer token, String password) throws UserNotFoundException, JsonProcessingException {

        // 1. Check if Token exists and is not expired yet
        log.debug("Checking if token is valid");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString());
        Optional<OneTimePassword> optionalOTP = oneTimePasswordRepository.findByValueAndExpiryDateTimeAfter(token, now);
        if (optionalOTP.isEmpty()) {throw new RuntimeException("Invalid token");}
        OneTimePassword oneTimePassword = optionalOTP.get();

        // 2. If Token is valid, get the user for that token
        log.debug("Get user for token: {}", oneTimePassword.getUser().getId());
        User user = oneTimePassword.getUser();

        // 3. Update to new password and save user to db
        log.debug("Save new password for user: {}", user.getId());
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public void requestPasswordReset(String email) throws UserNotFoundException, JsonProcessingException {
        // 1. Check if user exists
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        User user = optionalUser.get();

        // 2. Check if OTP already exists for that email
        Optional<OneTimePassword> optionalOTP = oneTimePasswordRepository.findOneTimePasswordByUser(user);
        log.debug("Deleting otp from DB");
        optionalOTP.ifPresent(oneTimePassword -> oneTimePasswordRepository.delete(oneTimePassword));

        // 3. If user exists, generate 4 digit Random Integer
        log.debug("Generating OTP for user: {}", email);
        Integer otp = RandomUtils.nextInt(9999);

        // 4. Save to DB
        log.debug("Saving otp to DB");
        OneTimePassword oneTimePassword = OneTimePassword.builder()
                .value(otp)
                .expiryDateTime(LocalDateTime.now().plusMinutes(5)) // OTP expires in 5 min
                .user(user)
                .build();
        oneTimePasswordRepository.save(oneTimePassword);

        // 5. Send to Kafka
        log.debug("Sending notification");
        PasswordResetNotification notification = PasswordResetNotification.getBuilder()
                .setUserId(user.getId())
                .setOtp(otp)
                .build();
        notificationService.send(notification);
    }
}
