package me.sridharpatil.ecom.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShippingAddressNotFoundException extends Exception {
    public ShippingAddressNotFoundException(String message) {
        super(message);
    }
}
