package me.sridharpatil.ecom.paymentservice.services.payment.paymentgateways.razorpay;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter @Setter
public class Customer {
    private String name;
    private String contact;
    private String email;

    public static CustomerBuilder getJSONObjectBuilder() {
        return new CustomerBuilder();
    }

    public static class CustomerBuilder{
        private String name;
        private String contact;
        private String email;

        public CustomerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder contact(String contact) {
            this.contact = contact;
            return this;
        }

        public CustomerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public JSONObject build() {
            JSONObject customer = new JSONObject();
            customer.put("name",this.name);
            customer.put("contact",this.contact);
            customer.put("email",this.email);

            return customer;
        }
    }
}
