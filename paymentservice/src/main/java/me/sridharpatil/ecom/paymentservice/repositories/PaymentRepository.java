package me.sridharpatil.ecom.paymentservice.repositories;

import me.sridharpatil.ecom.paymentservice.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
