package me.sridharpatil.ecom.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.factories.RecipientFactory;
import me.sridharpatil.ecom.notificationservice.models.EventType;
import me.sridharpatil.ecom.notificationservice.properties.NotificationProperties;
import me.sridharpatil.ecom.notificationservice.services.notifiers.Notifier;
import me.sridharpatil.ecom.notificationservice.services.order.Order;
import me.sridharpatil.ecom.notificationservice.services.order.OrderService;
import me.sridharpatil.ecom.notificationservice.services.user.User;
import me.sridharpatil.ecom.notificationservice.services.user.UserService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

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
    public void send(Recipient recipient, EventType eventType, Map<String, String> variables) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {
        notifier.send(recipient, eventType, variables);
    }

    @Override
    public void handleOrderCancelledEvent(Long userId, Long orderId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {

        User user = userService.getUserById(userId);

        Recipient recipient = RecipientFactory.getRecipient(user, notificationProperties.getNotifier());
        EventType eventType = EventType.ORDER_CANCELLED;
        Map<String, String> variables = Map.of("orderId", orderId.toString());

        this.send(recipient, eventType, variables);
    }

    @Override
    public void handleOrderConfirmedEvent(Long userId, Long orderId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {

        User user = userService.getUserById(userId);
        Order order = orderService.getOrderById(orderId);

        Recipient recipient = RecipientFactory.getRecipient(user, notificationProperties.getNotifier());
        EventType eventType = EventType.ORDER_CONFIRMED;
        Map<String, String> variables = Map.of("orderId", orderId.toString(), "totalPrice", order.getTotal().toString());

        this.send(recipient, eventType, variables);
    }
}
