package me.sridharpatil.ecom.paymentservice.services.payment.paymentgateways.razorpay;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter @Setter
public class PaymentLinkRequest {
    private double amount;
    private String currency;
    private long expire_by;
    private String reference_id;
    private String description;
    private String callback_url;
    private String callback_method;

    private JSONObject customer;
    private JSONObject notify;
    private JSONObject notes;


    public static PaymentLinkRequestBuilder getJSONObjectBuilder() {
        return new PaymentLinkRequestBuilder();
    }

    public static class PaymentLinkRequestBuilder{
        private double amount;
        private String currency;
        private long expire_by;
        private String reference_id;
        private String description;
        private String callback_url;
        private String callback_method;
        private JSONObject customer;
        private JSONObject notify;
        private JSONObject notes;

        public PaymentLinkRequestBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public PaymentLinkRequestBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public PaymentLinkRequestBuilder expire_by(long expire_by) {
            this.expire_by = expire_by;
            return this;
        }

        public PaymentLinkRequestBuilder reference_id(String reference_id) {
            this.reference_id = reference_id;
            return this;
        }

        public PaymentLinkRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PaymentLinkRequestBuilder callback_url(String callback_url) {
            this.callback_url = callback_url;
            return this;
        }

        public PaymentLinkRequestBuilder callback_method(String callback_method) {
            this.callback_method = callback_method;
            return this;
        }

        public PaymentLinkRequestBuilder customer(JSONObject customer) {
            this.customer = customer;
            return this;
        }

        public PaymentLinkRequestBuilder notify(JSONObject notify) {
            this.notify = notify;
            return this;
        }

        public PaymentLinkRequestBuilder notes(JSONObject notes) {
            this.notes = notes;
            return this;
        }

        public JSONObject build() {

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", (this.amount + 10.0) * 100 ); // amount in the smallest currency unit
            paymentLinkRequest.put("currency", this.currency);
            paymentLinkRequest.put("expire_by", this.expire_by);
            paymentLinkRequest.put("reference_id", this.reference_id);
            paymentLinkRequest.put("description", this.description);
            paymentLinkRequest.put("callback_url", this.callback_url);
            paymentLinkRequest.put("callback_method", this.callback_method);

            paymentLinkRequest.put("customer",this.customer);
            paymentLinkRequest.put("notify",this.notify);
            paymentLinkRequest.put("notes",this.notes);

            return paymentLinkRequest;
        }
    }

}
