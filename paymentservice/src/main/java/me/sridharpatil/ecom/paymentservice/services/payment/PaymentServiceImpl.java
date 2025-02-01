package me.sridharpatil.ecom.paymentservice.services.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.models.PaymentMode;
import me.sridharpatil.ecom.paymentservice.models.PaymentStatus;
import me.sridharpatil.ecom.paymentservice.properties.ConfigProperty;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentLinkRepository;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentRepository;
import me.sridharpatil.ecom.paymentservice.paymentgwadapters.PaymentGatewayAdapter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService{

    PaymentRepository paymentRepository;
    PaymentLinkRepository paymentLinkRepository;
    PaymentGatewayAdapter paymentGatewayAdapter;
    PaymentHelper paymentHelper;
    ConfigProperty configProperty;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PaymentLinkRepository paymentLinkRepository,
                              Map<String, PaymentGatewayAdapter> paymentGateways,
                              PaymentHelper paymentHelper,
                              ConfigProperty configProperty
                              ) {
        this.paymentRepository = paymentRepository;
        this.paymentLinkRepository = paymentLinkRepository;
        this.paymentHelper = paymentHelper;
        this.configProperty = configProperty;
        this.paymentGatewayAdapter = paymentGateways.get(this.configProperty.getPaymentGateway());
    }

    @Override
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException, JsonProcessingException {
        PaymentLink paymentLink = paymentGatewayAdapter.createPaymentLink(payment);

        paymentLinkRepository.save(paymentLink);
        return paymentLink;
    }

    @Override
    public PaymentLink getPaymentLinkByOrderId(Long orderId) {

        Payment payment = paymentRepository.findAllByOrderId(orderId).stream().findFirst().orElse(null);
        if (payment == null) {
            return null;
        }

        return payment.getPaymentLinks()
                .stream()
                .filter(pl -> pl.getPayment().getId().equals(payment.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public Payment getPaymentByPaymentLinkId(String paymentLinkId) {
        return paymentHelper.getPaymentByPaymentLinkId(paymentLinkId);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return paymentHelper.updatePayment(payment);
    }

    @Override
    public void handleCallback(Map<String, String> payload) throws RazorpayException, JsonProcessingException {

        paymentGatewayAdapter.handleCallback(payload);

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
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public PaymentStatus getPaymentStatus(Long paymentId) {
        if (getPayment(paymentId) != null) {
            return getPayment(paymentId).getPaymentStatus();
        }
        return null;
    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus) {

    }
}
