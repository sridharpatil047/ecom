package me.sridharpatil.ecom.paymentservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
public class Payment extends BaseModel{
    private Long orderId;
    private double amount;
    private PaymentStatus paymentStatus;
    private PaymentMode paymentMode;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private List<PaymentLink> paymentLinks;
}
