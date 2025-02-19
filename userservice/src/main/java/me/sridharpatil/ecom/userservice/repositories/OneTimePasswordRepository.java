package me.sridharpatil.ecom.userservice.repositories;

import me.sridharpatil.ecom.userservice.models.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {
}
