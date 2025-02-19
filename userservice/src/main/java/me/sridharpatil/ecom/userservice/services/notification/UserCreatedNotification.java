package me.sridharpatil.ecom.userservice.services.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter @Setter
public class UserCreatedNotification extends Notification {

    private UserCreatedNotification(Long userId) {
        this.to = "user-service.user-created";

        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        this.message = userId;

        log.debug("UserCreatedNotification: to - {} , message - {}", to, message);
    }

    public static UserCreatedNotificationBuilder getBuilder(){
        return new UserCreatedNotificationBuilder();
    }

    @Getter @Setter
    public static class UserCreatedNotificationBuilder {

        private Long userId;

        public UserCreatedNotificationBuilder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }
        public UserCreatedNotification build() {
            return new UserCreatedNotification(this.userId);
        }
    }
}
