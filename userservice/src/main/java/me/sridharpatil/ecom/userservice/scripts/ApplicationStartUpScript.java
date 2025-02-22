package me.sridharpatil.ecom.userservice.scripts;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.userservice.exceptions.RoleAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.exceptions.UserAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.UserNotFoundException;
import me.sridharpatil.ecom.userservice.models.Role;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.RoleService;
import me.sridharpatil.ecom.userservice.services.UserService;
import me.sridharpatil.ecom.userservice.services.dtos.UserDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Log4j2
@Component
public class ApplicationStartUpScript implements CommandLineRunner {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";

    private final RoleService roleService;
    private final UserService userService;


    public ApplicationStartUpScript(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws RoleAlreadyExistsException, UserNotFoundException, RoleNotFoundException, UserAlreadyExistsException, JsonProcessingException {
        System.out.println("\t\t\tInitial user registration");

        // 1. Create a Role - ADMIN
        String roleName = "ADMIN";
        String userRoleName = "USER";
        try {
            roleService.getRoleByName(roleName);
        } catch (RoleNotFoundException e) {
            roleService.createRole(roleName, "administrator access to the application");
            roleService.createRole(userRoleName, "limited access to the application");
        }
        System.out.println("\t\t\tRoles : "+ roleName + ", " + userRoleName);

        // 2. Create a user - admin.ecom@gmail.com
        String username = "admin";
        String password = "admin";
        String email = "admin.ecom@gmail.com";
        User user = null;
        try {
            user = userService.getUserByEmail(email);
        }catch (UserNotFoundException e) {
            user = userService.signUp(username, email, password);
        }
        System.out.println("\t\t\tName : " + username);
        System.out.println("\t\t\tEmail : " + email + " (use email for login)");
        System.out.println(RED + "\t\t\tPassword : " + password + RESET);


        // 3. Assign ADMIN role to admin.ecom@gmail.com
        userService.updateUser(user.getId(),
                UserDto.builder()
                        .roles(List.of(roleName))
                        .build());
        System.out.println("\t\t\tRole " + roleName + " assigned to " + email);

    }
}


















