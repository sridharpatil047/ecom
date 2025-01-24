package me.sridharpatil.ecom.userservice.repositories;

import me.sridharpatil.ecom.userservice.models.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    List<ShippingAddress> findAllByUserId(Long userId);
}
