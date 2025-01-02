package me.sridharpatil.productservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Category extends BaseModel{
    private String title;
}
