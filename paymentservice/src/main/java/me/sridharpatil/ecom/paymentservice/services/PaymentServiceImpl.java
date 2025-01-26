package me.sridharpatil.ecom.paymentservice.services;

import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.models.PaymentMode;
import me.sridharpatil.ecom.paymentservice.models.PaymentStatus;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentRepository;
import me.sridharpatil.ecom.paymentservice.services.stratagy.PaymentGatewayContext;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    PaymentRepository paymentRepository;
    PaymentGatewayContext paymentGatewayContext;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentGatewayContext paymentGatewayContext) {
        this.paymentRepository = paymentRepository;
        this.paymentGatewayContext = paymentGatewayContext;
    }

    @Override
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException {
        return paymentGatewayContext.createPaymentLink(payment);
    }

    @Override
    public PaymentLink getPaymentLink(String link) {
        return null;
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
