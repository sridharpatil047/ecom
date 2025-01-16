package me.sridharpatil.ecom.userservice.services;

import me.sridharpatil.ecom.userservice.exceptions.RoleAlreadyExistsException;
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
    public void createRole(String name, String description) throws RoleAlreadyExistsException {

        // Check if role already exists
        if (roleRepository.findRoleByName(name).isPresent()) {
            throw new RoleAlreadyExistsException("Role with name " + name + " already exists");
        }

        // Since role does not exist, create a new role
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);

        // Save the role
        roleRepository.save(role);
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
