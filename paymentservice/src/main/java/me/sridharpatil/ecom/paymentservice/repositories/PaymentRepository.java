package me.sridharpatil.ecom.paymentservice.repositories;

import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);

}
