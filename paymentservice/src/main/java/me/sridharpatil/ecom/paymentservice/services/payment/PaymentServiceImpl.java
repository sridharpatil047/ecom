package me.sridharpatil.ecom.paymentservice.services.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.models.PaymentMode;
import me.sridharpatil.ecom.paymentservice.models.PaymentStatus;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentLinkRepository;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentRepository;
import me.sridharpatil.ecom.paymentservice.services.payment.paymentgateways.PaymentGatewayContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{

    PaymentRepository paymentRepository;
    PaymentLinkRepository paymentLinkRepository;
    PaymentGatewayContext paymentGatewayContext;


    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentLinkRepository paymentLinkRepository, PaymentGatewayContext paymentGatewayContext) {
        this.paymentRepository = paymentRepository;
        this.paymentLinkRepository = paymentLinkRepository;
        this.paymentGatewayContext = paymentGatewayContext;
    }

    @Override
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException, JsonProcessingException {
        PaymentLink paymentLink = paymentGatewayContext.createPaymentLink(payment);

        paymentLinkRepository.save(paymentLink);
        return paymentGatewayContext.createPaymentLink(payment);
    }

    @Override
    public PaymentLink getPaymentLinkByOrderId(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId).orElse(null);
        if (payment == null) {
            return null;
        }

        return payment.getPaymentLinks()
                .stream()
                .filter(pl -> pl.getPayment().getId().equals(payment.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public Payment createPayment(Long orderId, Double amount, PaymentMode paymentMode) {

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentMode(paymentMode);
        payment.setPaymentStatus(PaymentStatus.CREATED);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long paymentId) {
        return null;
    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus) {

    }
}
