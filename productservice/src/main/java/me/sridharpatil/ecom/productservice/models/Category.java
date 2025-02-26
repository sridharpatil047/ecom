package me.sridharpatil.ecom.productservice.models;

import jakarta.persistence.Entity;
import lombok.*;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Category extends BaseModel{
    private String title;
}
