package me.sridharpatil.ecom.paymentservice.services.payment;

import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentLinkRepository;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentHelper {

    PaymentRepository paymentRepository;
    PaymentLinkRepository paymentLinkRepository;


    public PaymentHelper(PaymentRepository paymentRepository, PaymentLinkRepository paymentLinkRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentLinkRepository = paymentLinkRepository;
    }

    public Payment getPaymentByPaymentLinkId(String paymentLinkId) {
        return paymentLinkRepository.findByIdentifier(paymentLinkId)
                .getPayment();
    }

    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
