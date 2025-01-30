package me.sridharpatil.ecom.paymentservice.repositories;

import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLinkRepository extends JpaRepository<PaymentLink, Long> {
    PaymentLink findByIdentifier(String identifier);
}
