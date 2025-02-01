package me.sridharpatil.ecom.paymentservice.paymentgwadapters.razorpay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.models.PaymentStatus;
import me.sridharpatil.ecom.paymentservice.paymentgwadapters.PaymentGatewayAdapter;
import me.sridharpatil.ecom.paymentservice.paymentgwadapters.razorpay.customobjects.Customer;
import me.sridharpatil.ecom.paymentservice.paymentgwadapters.razorpay.customobjects.Notes;
import me.sridharpatil.ecom.paymentservice.paymentgwadapters.razorpay.customobjects.Notify;
import me.sridharpatil.ecom.paymentservice.paymentgwadapters.razorpay.customobjects.PaymentLinkRequest;
import me.sridharpatil.ecom.paymentservice.properties.ConfigProperty;
import me.sridharpatil.ecom.paymentservice.repositories.PaymentRepository;
import me.sridharpatil.ecom.paymentservice.services.order.OrderService;
import me.sridharpatil.ecom.paymentservice.services.payment.PaymentHelper;
import me.sridharpatil.ecom.paymentservice.services.payment.PaymentService;
import me.sridharpatil.ecom.paymentservice.services.producers.PaymentProducer;
import me.sridharpatil.ecom.paymentservice.services.user.UserService;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log4j2
@Component(value = "razorpay")
public class RazorpayPaymentGatewayAdapter implements PaymentGatewayAdapter {
    RazorpayClient razorpayClient;
    ConfigProperty configProperty;
    OrderService orderService;
    UserService userService;
    PaymentHelper paymentHelper;
    PaymentProducer paymentProducer;

    public RazorpayPaymentGatewayAdapter(RazorpayClient razorpayClient, ConfigProperty configProperty, OrderService orderService, UserService userService, PaymentHelper paymentHelper, PaymentProducer paymentProducer) {
        this.razorpayClient = razorpayClient;
        this.configProperty = configProperty;
        this.orderService = orderService;
        this.userService = userService;
        this.paymentHelper = paymentHelper;
        this.paymentProducer = paymentProducer;
    }

    @Override
    public PaymentLink createPaymentLink(Payment payment) throws RazorpayException, JsonProcessingException {

        // get user details from order service and user service
        Long userId = orderService.getUserIdByOrderId(payment.getOrderId());
        String userName = userService.getUserName(userId);
        String userEmail = userService.getUserEmail(userId);

        // create customer, notify and notes objects
        JSONObject customer = Customer.getJSONObjectBuilder()
                .name(userName)
                .contact("+91 9999999999")
                .email(userEmail)
                .build();
        JSONObject notify = Notify.getJSONObjectBuilder()
                .sms(true)
                .email(true)
                .reminder_enable(true)
                .build();
        JSONObject notes = Notes.getJSONObjectBuilder()
                .policyName("Jeevan Bima")
                .build();

        // create payment link request object
        JSONObject request = PaymentLinkRequest.getJSONObjectBuilder()
                .amount(payment.getAmount() * 100) // amount in the smallest currency unit
                .currency("INR")
                .expire_by(System.currentTimeMillis()/1000 + configProperty.getPaymentLinkExpiryInMinutes() * 60)
//                .reference_id(payment.getOrderId().toString())
                .description("Payment for order " + payment.getOrderId())
                .callback_url("http://localhost:8086/payments/razorpay/callback")
                .callback_method("get")
                .customer(customer)
                .notify(notify)
                .notes(notes)
                .build();

        // create payment link Razorpay object
        com.razorpay.PaymentLink razorpayPaymentLink = razorpayClient.paymentLink.create(request);

        // create payment link Own object
        PaymentLink paymentLink = new PaymentLink();
        paymentLink.setLink(razorpayPaymentLink.get("short_url").toString());
        paymentLink.setIdentifier(razorpayPaymentLink.get("id").toString());
        paymentLink.setPayment(payment);

        // send payment link to kafka
//        paymentProducer.paymentLinkCreatedEvent(paymentLink);

        return paymentLink;
    }

    @Override
    public void handleCallback(Map<String, String> payload) throws RazorpayException, JsonProcessingException {
        String razorpayPaymentId = payload.get("razorpay_payment_id");
        String razorpayPaymentLinkId = payload.get("razorpay_payment_link_id");

        System.out.println(razorpayPaymentId);
        System.out.println(razorpayPaymentLinkId);

        String paymentStatus = razorpayClient.payments.fetch(razorpayPaymentId)
                .get("status");

        Payment payment = paymentHelper.getPaymentByPaymentLinkId(razorpayPaymentLinkId);
        payment.setIdentifier(razorpayPaymentId);

        if (paymentStatus.equals("captured")) {
            log.info("Payment successful for order: " + payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.valueOf(paymentStatus.toUpperCase()));
            paymentProducer.paymentSuccessEvent(payment.getOrderId());
        } else if (paymentStatus.equals("failed")) {
            log.error("Payment failed for order: " + payment.getOrderId());
            payment.setPaymentStatus(PaymentStatus.valueOf(paymentStatus.toUpperCase()));
            paymentProducer.paymentFailedEvent(payment.getOrderId(), "Payment failed");
        }
        paymentHelper.updatePayment(payment);



    }
}
