package me.sridharpatil.ecom.paymentservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
public class Payment extends BaseModel{
    private String identifier;
    private Long orderId;
    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private List<PaymentLink> paymentLinks;
}
