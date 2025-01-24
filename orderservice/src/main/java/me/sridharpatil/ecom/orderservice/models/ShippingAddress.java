package me.sridharpatil.ecom.orderservice.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Embeddable
public class ShippingAddress {
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private boolean active;
}
