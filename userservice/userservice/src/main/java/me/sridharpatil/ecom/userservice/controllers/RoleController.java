package me.sridharpatil.ecom.userservice.controllers;

import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.models.Role;
import me.sridharpatil.ecom.userservice.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // 1. POST /roles
    ResponseEntity<Role> createRole(Role role) {
        return ResponseEntity.ok(roleService.createRole(role.getName(), role.getDescription()));
    }

    // 2. GET /roles/{name}
    ResponseEntity<Role> getRoleByName(String name) throws RoleNotFoundException {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    // 3. GET /roles
    ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
}
