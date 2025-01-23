package me.sridharpatil.ecom.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Role extends BaseModel{
    private String name;
    private String description;
}
