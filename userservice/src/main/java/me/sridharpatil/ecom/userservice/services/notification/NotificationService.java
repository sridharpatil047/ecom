package me.sridharpatil.ecom.userservice.services.notification;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationService {
    void send(Notification notification) throws JsonProcessingException;
}
