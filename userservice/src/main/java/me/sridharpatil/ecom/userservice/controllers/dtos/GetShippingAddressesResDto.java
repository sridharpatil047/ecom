package me.sridharpatil.ecom.userservice.controllers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.userservice.models.ShippingAddress;

@Builder
@Getter @Setter
public class GetShippingAddressesResDto {
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private boolean active;

    public static GetShippingAddressesResDto of(ShippingAddress shippingAddress) {
        return GetShippingAddressesResDto.builder()
                .street(shippingAddress.getStreet())
                .city(shippingAddress.getCity())
                .state(shippingAddress.getState())
                .country(shippingAddress.getCountry())
                .pinCode(shippingAddress.getPinCode())
                .active(shippingAddress.isActive())
                .build();
    }
}
