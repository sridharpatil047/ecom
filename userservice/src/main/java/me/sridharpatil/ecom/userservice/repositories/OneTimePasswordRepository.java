package me.sridharpatil.ecom.userservice.repositories;

import me.sridharpatil.ecom.userservice.models.OneTimePassword;
import me.sridharpatil.ecom.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {
    Optional<OneTimePassword> findOneTimePasswordByUser(User user);
    Optional<OneTimePassword> findByValueAndExpiryDateTimeAfter(Integer value, LocalDateTime now);
}
