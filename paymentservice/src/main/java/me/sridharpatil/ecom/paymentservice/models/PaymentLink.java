package me.sridharpatil.ecom.paymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class PaymentLink extends BaseModel{
    private String link;
    private LocalDateTime expiry;
    private String status;

    @ManyToOne
    private Payment payment;
}
