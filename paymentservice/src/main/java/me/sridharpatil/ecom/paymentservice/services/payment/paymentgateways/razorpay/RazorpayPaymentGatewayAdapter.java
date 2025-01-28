package me.sridharpatil.ecom.paymentservice.services.payment.paymentgateways.razorpay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import me.sridharpatil.ecom.paymentservice.models.Payment;
import me.sridharpatil.ecom.paymentservice.models.PaymentLink;
import me.sridharpatil.ecom.paymentservice.properties.ConfigProperty;
import me.sridharpatil.ecom.paymentservice.services.order.OrderService;
import me.sridharpatil.ecom.paymentservice.services.payment.paymentgateways.PaymentGateway;
import me.sridharpatil.ecom.paymentservice.services.producers.PaymentProducer;
import me.sridharpatil.ecom.paymentservice.services.user.UserService;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component(value = "razorpay")
public class RazorpayPaymentGatewayAdapter implements PaymentGateway {
    RazorpayClient razorpayClient;
    ConfigProperty configProperty;
    OrderService orderService;
    UserService userService;
    PaymentProducer paymentProducer;

    public RazorpayPaymentGatewayAdapter(RazorpayClient razorpayClient, ConfigProperty configProperty, OrderService orderService, UserService userService, PaymentProducer paymentProducer) {
        this.razorpayClient = razorpayClient;
        this.configProperty = configProperty;
        this.orderService = orderService;
        this.userService = userService;
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
                .amount(payment.getAmount() * 100)
                .currency("INR")
                .expire_by(System.currentTimeMillis()/1000 + configProperty.getPaymentLinkExpiryInMinutes() * 60)
//                .reference_id(payment.getOrderId().toString())
                .description("Payment for order " + payment.getOrderId())
                .callback_url("https://google.com/")
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
}
