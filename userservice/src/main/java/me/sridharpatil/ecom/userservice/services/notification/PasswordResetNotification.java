package me.sridharpatil.ecom.userservice.services.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter @Setter
public class PasswordResetNotification extends Notification {
    public PasswordResetNotification(Long userId, Integer otp) {
        this.to = "user-service.password-resets";
        this.message = new Message(userId, otp);
    }

    public static PasswordResetNotificationBuilder getBuilder(){
        return new PasswordResetNotificationBuilder();
    }

    @Getter @Setter
    public static class PasswordResetNotificationBuilder {
        Long userId;
        Integer otp;

        public PasswordResetNotificationBuilder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public PasswordResetNotificationBuilder setOtp(Integer otp) {
            this.otp = otp;
            return this;
        }

        public PasswordResetNotification build() {
            return new PasswordResetNotification(this.userId, this.otp);
        }
    }

    @AllArgsConstructor
    @Getter @Setter
    public static class Message{
        private Long userId;
        private Integer otp;
    }
}
