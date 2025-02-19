package me.sridharpatil.ecom.userservice.services.notification;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Notification {
    String to; // Kafka topic for now, can be anything in future
    Object message;
}
