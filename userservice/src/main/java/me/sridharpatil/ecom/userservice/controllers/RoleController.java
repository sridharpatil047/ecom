package me.sridharpatil.ecom.userservice.controllers;

import me.sridharpatil.ecom.userservice.exceptions.RoleAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.models.Role;
import me.sridharpatil.ecom.userservice.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;



@RestController
@RequestMapping("/roles")
// Since `Role` can exist without `User`, we have separate controller for `Role`,
// however, `User` controller has `roles` endpoint to manage roles of a user.
// For `ShippingAddress`, it can exist only with `User`, so it is managed in `User` controller.
public class RoleController {
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // 1. POST /roles
    @PostMapping()
    ResponseEntity<String> createRole(@RequestBody Role role) throws RoleAlreadyExistsException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        roleService.createRole(role.getName(), role.getDescription());
        return ResponseEntity.ok("Role created successfully");
    }

    // 2. GET /roles/{name}
    @GetMapping("/{name}")
    ResponseEntity<Role> getRoleByName(@PathVariable String name) throws RoleNotFoundException {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    // 3. GET /roles
    @GetMapping()
    ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
}
