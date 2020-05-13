package ro.siit.OrderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.siit.OrderDetails.Billing;

@Data //free getters and setters
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private int id;
    private Billing billing;
    private String date_created;
    private int total;
    private String payment_method_title;
    private Products products;

    @Override
    public String toString() {
        return "Order " + billing.getFirst_name() + " " + billing.getLast_name() + " id " + id;
    }
}

