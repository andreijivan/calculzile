package ro.siit.OrderDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //free getters and setters
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Billing {
   private String first_name;
    private String last_name;
    private String address_1;
    private String address_2;
    private String city;
    private String state;
    private String postcode;
    private String country;
    private String email;
    private String phone;
}
