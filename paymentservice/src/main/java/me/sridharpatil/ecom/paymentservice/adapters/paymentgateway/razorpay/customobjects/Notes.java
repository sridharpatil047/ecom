package me.sridharpatil.ecom.paymentservice.adapters.paymentgateway.razorpay.customobjects;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter @Setter
public class Notes {
    private String policyName;

    public static NotesBuilder getJSONObjectBuilder() {
        return new NotesBuilder();
    }

    public static class NotesBuilder{
        private String policyName;

        public NotesBuilder policyName(String policyName) {
            this.policyName = policyName;
            return this;
        }

        public JSONObject build() {
            JSONObject notes = new JSONObject();
            notes.put("policy_name",this.policyName);
            return notes;
        }
    }
}
