package ro.siit.OrderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //free getters and setters
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private int id;
    private Billing billing;
    private String date_created;
    private int total;
    private int shipping_total;
    private String payment_method_title;
    private LineItems [] line_items;
    private ShippingLine [] shipping_lines;
    private String customer_note;


}

