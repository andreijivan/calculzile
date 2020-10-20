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

    private Connection connection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if ("POST".equalsIgnoreCase(req.getMethod())) {
            String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            ObjectMapper objectMapper = new ObjectMapper();
            Order order = objectMapper.readValue(test, Order.class);

            int Nr = order.getId();
            int codComanda = order.getId();
            String data = order.getDate_created();
            String client = order.getBilling().getFirst_name() + " " + order.getBilling().getLast_name();
            String produse = "";
            for (int i = 0; i < order.getLine_items().length; i++) {
                produse += (order.getLine_items()[i].getQuantity()) + " x " + (order.getLine_items()[i].getName()) + "(" + (order.getLine_items()[i].getTotal()) + " lei)" + "|| ";
            }
            String adresa = order.getBilling().getAddress_1() + " " + order.getBilling().getAddress_2();
            String localitate = order.getBilling().getCity() + " " + order.getBilling().getState();
            String codPostal = order.getBilling().getPostcode();
            String tara = order.getBilling().getCountry();
            String nrTelefon = order.getBilling().getPhone();
            String email = order.getBilling().getEmail();
            String observatii = order.getCustomer_note();
            int valoareProduse = order.getTotal() - order.getShipping_total();
            int valoareLivrare = order.getShipping_total();
            String status = "";
            String incasat = String.valueOf(valoareProduse);
            String state = "pending";
            String currency = order.getCurrency();

            if (order.getPayment_method_title().equals("Plata cu cardul / Card payment") || order.getPayment_method_title().equals("Plata cu cardul")) {
                status = "Achitat online CARD. De expediat. *Create AWB*";
                if (order.getShipping_lines().length>0 && order.getShipping_lines()[0].getMethod_title().equals("Ridicare personală de la depozitul magazinului (fără cost de transport)")){
                    status ="Achitat online CARD. Ridicare personala depozit";
                }
            } else if (order.getPayment_method_title().equals("Transfer bancar (ordin de plată) / Direct bank transfer")) {
                status = "Achitat online TRANSFER BANCAR. De asteptat confirmare banca. De expediat. *Create AWB*";
            }

            if (order.getPayment_method_title().equals("Plata numerar")) {
                if (order.getShipping_lines()[0].getMethod_title().equals("Transport prin curier rapid")) {
                    status = "Plata ramburs. Livrare curier  *Create AWB*";
                } else if (order.getShipping_lines()[0].getMethod_title().equals("Ridicare personală de la depozitul magazinului (fără cost de transport)")) {
                    status = "Plata ramburs. Ridicare personala din magazin.";
                }
            }

            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement ps;
                // daca nu avem cont euro:
                // solutie noua: daca currency = euro, produse = produse.replaceAll("lei","euro"), pus tot in poliorders/produsevirtuale,
                // si conversat in lei cand e finalizata comanda, sau direct conversat in lei daca e prod virtual
                if (currency.equals("RON")){
                    if ((produse.contains("Bilet virtual") && produse.length() < 90) || (produse.contains("Cutia virtual") && produse.length() < 90)) {
                        state = "finalizat";
                        ps = connection.prepareStatement
                                ("INSERT INTO produsevirtuale (status, nr, cod_comanda, data_comanda, client, produse, adresa, localitate,cod_postal, tara, telefon, email, observatii, valoare_produse, incasat, state, cost_transport) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    } else {
                        ps = connection.prepareStatement
                                ("INSERT INTO poliorders (status, nr, cod_comanda, data_comanda, client, produse, adresa, localitate,cod_postal, tara, telefon, email, observatii, valoare_produse, incasat, state, cost_transport) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    }
                    insertDB(Nr, codComanda, data, client, produse, adresa, localitate, codPostal, tara, nrTelefon, email, observatii, valoareProduse, valoareLivrare, status, incasat, state, ps);
                }
                else if(currency.equals("EUR")){
                    produse = produse.replaceAll("lei","euro");
                    ps = connection.prepareStatement
                            ("INSERT INTO poliorderseuro (status, nr, cod_comanda, data_comanda, client, produse, adresa, localitate,cod_postal, tara, telefon, email, observatii, valoare_produse, incasat, state, cost_transport) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    insertDB(Nr, codComanda, data, client, produse, adresa, localitate, codPostal, tara, nrTelefon, email, observatii, valoareProduse, valoareLivrare, status, incasat, state, ps);
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }
            }
        }
    }

    private void insertDB(int nr, int codComanda, String data, String client, String produse, String adresa, String localitate, String codPostal, String tara, String nrTelefon, String email, String observatii, int valoareProduse, int valoareLivrare, String status, String incasat, String state, PreparedStatement ps) throws SQLException {
        ps.setString(1, status);
        ps.setInt(2, nr);
        ps.setInt(3, codComanda);
        ps.setString(4, data);
        ps.setString(5, client);
        ps.setString(6, produse);
        ps.setString(7, adresa);
        ps.setString(8, localitate);
        ps.setString(9, codPostal);
        ps.setString(10, tara);
        ps.setString(11, nrTelefon);
        ps.setString(12, email);
        ps.setString(13, observatii);
        ps.setInt(14, valoareProduse);
        ps.setString(15, incasat);
        ps.setString(16, state);
        ps.setString(17, String.valueOf(valoareLivrare));
        ps.executeUpdate();
        ps.close();
    }
}
