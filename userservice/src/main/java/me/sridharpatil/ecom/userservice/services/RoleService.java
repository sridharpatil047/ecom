package me.sridharpatil.ecom.userservice.services;

import me.sridharpatil.ecom.userservice.exceptions.RoleAlreadyExistsException;
import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.models.Role;

import java.util.List;

public interface RoleService {
    void createRole(String role, String description) throws RoleAlreadyExistsException;
    Role getRoleByName(String name) throws RoleNotFoundException;

    List<Role> getRoles();
}
