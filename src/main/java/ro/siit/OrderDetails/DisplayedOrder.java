package ro.siit.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //free getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class DisplayedOrder {
     private String status;
     private int nr;
     private int codComanda;
     private String dataComanda;
     private String client;
     private String produse;
     private String adresa;
     private String localitate;
     private String codPostal;
     private String tara;
     private String telefon;
     private String email;
     private String observatii;
     private int valoareProduse;
     private String incasat;
     private String state;
     private String valoareLivrare;

}

