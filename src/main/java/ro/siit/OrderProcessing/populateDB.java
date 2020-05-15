package ro.siit.OrderProcessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.siit.OrderDetails.Order;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;


@WebServlet(urlPatterns = {"/populateDB"})
public class populateDB extends HttpServlet {

       @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

       }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if ("POST".equalsIgnoreCase(req.getMethod()))
        {
            String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
          /*  Pattern start = Pattern.compile("line_items");
            Pattern end = Pattern.compile("tax_lines");
            Matcher matchStart = start.matcher(test);
            Matcher matchEnd = end.matcher(test);
            String itemsList = test.substring(matchStart.start(),matchEnd.start());*/

            //jackson object conversion
            ObjectMapper objectMapper = new ObjectMapper();
            Order order = objectMapper.readValue(test,Order.class);


            int Nr = order.getId();
            int codComanda =order.getId();
            String Data = order.getDate_created();
            String Client = order.getBilling().getFirst_name() + " " + order.getBilling().getLast_name();
            String Produse = "";

            for (int i = 0; i <order.getLine_items().length ; i++) {
                Produse+= order.getLine_items()[i].getQuantity() + " x " + order.getLine_items()[i].getName() + "(" + order.getLine_items()[i].getTotal() + " lei)" + "|| ";
            }

            String Adresa = order.getBilling().getAddress_1() + " " + order.getBilling().getAddress_2();
            String Localitate = order.getBilling().getCity() + " " + order.getBilling().getState();
            String codPostal = order.getBilling().getPostcode();
            String Tara = order.getBilling().getCountry();
            String nrTelefon = order.getBilling().getPhone();
            String email = order.getBilling().getEmail();
            String Observatii = order.getCustomer_note(); //implement open field to save text
            int valoareProduse = order.getTotal()-order.getShipping_total();
            String Status = "";
            String incasat = order.getPayment_method_title();

            if (order.getPayment_method_title().equals("Plata cu cardul / Card payment") || order.getPayment_method_title().equals("Plata cu cardul") ){
                Status = "Achitat online CARD. De expediat. *Create AWB*";
                incasat = String.valueOf(valoareProduse);
            }
            else if (order.getPayment_method_title().equals("Transfer bancar (ordin de plată) / Direct bank transfer")){
                Status = "Achitat online TRANSFER BANCAR. De asteptat confirmare banca. De expediat. *Create AWB*";
            }

             if (order.getPayment_method_title().equals("Plata numerar")){
                 if (order.getShipping_lines()[0].getMethod_title().equals("Transport prin curier rapid")){
                Status = "Plata ramburs. Livrare curier  *Create AWB*";
            }
                 else if (order.getShipping_lines()[0].getMethod_title().equals("Ridicare personală de la depozitul magazinului (fără cost de transport)")){
                     Status = "Plata ramburs. Ridicare personala din magazin.";
             }
        }

            try{
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO poliorders (status, nr, cod_comanda, data_comanda, client, produse, adresa, localitate,cod_postal, tara, telefon, email, observatii, valoare_produse, incasat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, Status);
                ps.setInt(2, Nr);
                ps.setInt(3, codComanda);
                ps.setString(4,Data);
                ps.setString(5,Client);
                ps.setString(6,Produse);
                ps.setString(7,Adresa);
                ps.setString(8,Localitate);
                ps.setString(9,codPostal);
                ps.setString(10,Tara);
                ps.setString(11,nrTelefon);
                ps.setString(12,email);
                ps.setString(13,Observatii);
                ps.setInt(14,valoareProduse);
                ps.setString(15,incasat);

                ps.executeUpdate();

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

   /* private static Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }*/

}
