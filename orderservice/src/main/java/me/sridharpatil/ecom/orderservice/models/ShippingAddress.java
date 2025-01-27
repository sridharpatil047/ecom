package me.sridharpatil.ecom.orderservice.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@Embeddable
public class ShippingAddress implements Serializable {
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private boolean active;
}
