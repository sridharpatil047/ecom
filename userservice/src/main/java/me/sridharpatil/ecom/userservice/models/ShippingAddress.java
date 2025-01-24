package me.sridharpatil.ecom.userservice.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class ShippingAddress extends BaseModel {
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;

    private boolean active;

    @ManyToOne
    private User user;

}
