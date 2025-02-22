package me.sridharpatil.ecom.notificationservice.services.notifiers.templates;

import lombok.Builder;

@Builder
public class PasswordResetMessage implements Message{
    private Integer otp;
    @Override
    public String getSubject() {
        return "Password Reset Request";
    }

    @Override
    public String getBody() {
        return "Your OTP is: " + otp;
    }
}
