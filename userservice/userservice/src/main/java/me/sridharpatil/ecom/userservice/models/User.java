package me.sridharpatil.ecom.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String hashedPassword;
    private boolean emailVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
