package me.sridharpatil.ecom.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.factories.RecipientFactory;
import me.sridharpatil.ecom.notificationservice.properties.NotificationProperties;
import me.sridharpatil.ecom.notificationservice.services.notifiers.Notifier;
import me.sridharpatil.ecom.notificationservice.services.notifiers.templates.*;
import me.sridharpatil.ecom.notificationservice.services.order.Order;
import me.sridharpatil.ecom.notificationservice.services.order.OrderService;
import me.sridharpatil.ecom.notificationservice.services.user.User;
import me.sridharpatil.ecom.notificationservice.services.user.UserService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService{

    Notifier notifier;
    NotificationProperties notificationProperties;
    UserService userService;
    OrderService orderService;

    public NotificationServiceImpl(Map<String, Notifier> notifiers, NotificationProperties notificationProperties, UserService userService, OrderService orderService) {
        this.notificationProperties = notificationProperties;
        this.notifier = notifiers.get(this.notificationProperties.getNotifier());
        this.userService = userService;
        this.orderService = orderService;
    }


    @Override
    public void send(Recipient recipient, Message message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {
        notifier.send(recipient, message);
    }

    @Override
    public void handleOrderCancelledEvent(Long userId, Long orderId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {

        User user = userService.getUserById(userId);

        Recipient recipient = RecipientFactory.getRecipient(user, notificationProperties.getNotifier());
        Message message = OrderCancelledMessage.builder().build();
        this.send(recipient, message);
    }

    @Override
    public void handleOrderConfirmedEvent(Long userId, Long orderId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {

        User user = userService.getUserById(userId);
        Order order = orderService.getOrderById(orderId);

        Recipient recipient = RecipientFactory.getRecipient(user, notificationProperties.getNotifier());
        OrderConfirmedMessage message = OrderConfirmedMessage.builder()
                .orderId(orderId)
                .totalPrice(order.getTotal())
                .build();
        this.send(recipient, message);
    }

    @Override
    public void handleUserCreatedEvent(Long userId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {

        log.debug("Received user created event");

        User user = userService.getUserById(userId);

        Recipient recipient = RecipientFactory.getRecipient(user, notificationProperties.getNotifier());
        UserCreatedMessage message = UserCreatedMessage.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .build();

        this.send(recipient, message);
    }

    @Override
    public void handlePasswordResetEvent(Long userId, Integer otp) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {

        log.debug("Received password reset event");
        User user = userService.getUserById(userId);
        Recipient recipient = RecipientFactory.getRecipient(user, notificationProperties.getNotifier());

        PasswordResetMessage message = PasswordResetMessage.builder()
                .otp(otp)
                .build();
        this.send(recipient, message);
    }
}
