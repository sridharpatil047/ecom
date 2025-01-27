package me.sridharpatil.ecom.paymentservice.services.payment.paymentgateways.razorpay;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter @Setter
public class Notify {
    private Boolean sms;
    private Boolean email;
    private Boolean reminderEnable;

    public static NotifyBuilder getJSONObjectBuilder() {
        return new NotifyBuilder();
    }

    public static class NotifyBuilder{
        private Boolean sms;
        private Boolean email;
        private Boolean reminderEnable;

        public NotifyBuilder sms(boolean sms) {
            this.sms = sms;
            return this;
        }

        public NotifyBuilder email(boolean email) {
            this.email = email;
            return this;
        }

        public NotifyBuilder reminder_enable(boolean reminderEnable) {
            this.reminderEnable = reminderEnable;
            return this;
        }

        public JSONObject build() {
            JSONObject notify = new JSONObject();
            notify.put("sms",this.sms.toString());
            notify.put("email",this.email.toString());
            notify.put("reminder_enable",this.reminderEnable.toString());

            return notify;
        }
    }
}
