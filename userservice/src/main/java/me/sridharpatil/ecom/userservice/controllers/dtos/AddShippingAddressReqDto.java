package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddShippingAddressReqDto {
    private String country;
    private String state;
    private String city;
    private String street;
    private String pinCode;
}
