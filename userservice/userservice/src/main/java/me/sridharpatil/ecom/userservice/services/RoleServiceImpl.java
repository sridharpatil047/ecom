package me.sridharpatil.ecom.userservice.services;

import me.sridharpatil.ecom.userservice.exceptions.RoleNotFoundException;
import me.sridharpatil.ecom.userservice.models.Role;
import me.sridharpatil.ecom.userservice.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String name, String description) {

        // Check if role already exists
        if (roleRepository.findRoleByName(name).isPresent()) {
            return roleRepository.findRoleByName(name).get();
        }

        // Since role does not exist, create a new role
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);

        // Save the role
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String name) throws RoleNotFoundException {
        return roleRepository.findRoleByName(name).orElseThrow(
                () -> new RoleNotFoundException("Role with name " + name + " not found")
        );
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
