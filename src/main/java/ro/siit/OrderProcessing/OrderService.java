package ro.siit.OrderProcessing;

import ro.siit.OrderDetails.DisplayedOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderService {

        private Connection connection;

    public OrderService() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public List<DisplayedOrder> getAllOrders(){
         List<DisplayedOrder> orders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                 String status = rs.getString(1);
                 int nr = rs.getInt(2);
                 int codComanda = rs.getInt(3);
                 String dataComanda = rs.getString(4);
                 String client = rs.getString(5);
                 String produse = rs.getString(6);
                 String adresa = rs.getString(7);
                 String localitate = rs.getString(8);
                 String codPostal = rs.getString(9);
                 String tara = rs.getString(10);
                 String telefon = rs.getString(11);
                 String email = rs.getString(12);
                 String observatii = rs.getString(13);
                 int valoareProduse = rs.getInt(14);
                 String incasat = rs.getString(15);
                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat);
                orders.add(checkOrder);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Collections.sort(orders, Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return orders;
    }

     public List<DisplayedOrder> displayFinalizedOrders(){
         List<DisplayedOrder> finalizedOrders = new ArrayList<>();
         try {
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM comenzifinalizate");
             displayOrderList(finalizedOrders, ps);
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
         return finalizedOrders;
     }

    public List<DisplayedOrder> displayVirtualOrders(){
        List<DisplayedOrder> virtualOrders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM produsevirtuale");
            displayOrderList(virtualOrders, ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Collections.sort(virtualOrders, Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return virtualOrders;
    }

    private void displayOrderList(List<DisplayedOrder> finalizedOrders, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String status = rs.getString(1);
            int nr = rs.getInt(2);
            int codComanda = rs.getInt(3);
            String dataComanda = rs.getString(4);
            String client = rs.getString(5);
            String produse = rs.getString(6);
            String adresa = rs.getString(7);
            String localitate = rs.getString(8);
            String codPostal = rs.getString(9);
            String tara = rs.getString(10);
            String telefon = rs.getString(11);
            String email = rs.getString(12);
            String observatii = rs.getString(13);
            int valoareProduse = rs.getInt(14);
            String incasat = rs.getString(15);

            finalizedOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat));


        }
    }

    public List<DisplayedOrder> displayLocalOrders(){
        List<DisplayedOrder> localOrders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String status = rs.getString(1);
                int nr = rs.getInt(2);
                int codComanda = rs.getInt(3);
                String dataComanda = rs.getString(4);
                String client = rs.getString(5);
                String produse = rs.getString(6);
                String adresa = rs.getString(7);
                String localitate = rs.getString(8);
                String codPostal = rs.getString(9);
                String tara = rs.getString(10);
                String telefon = rs.getString(11);
                String email = rs.getString(12);
                String observatii = rs.getString(13);
                int valoareProduse = rs.getInt(14);
                String incasat = rs.getString(15);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat);
                if (checkOrder.getLocalitate().contains("TM")){
                    localOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Collections.sort(localOrders, Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return localOrders;
    }

    public List<DisplayedOrder> displayNationalOrders(){
        List<DisplayedOrder> nationalOrders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String status = rs.getString(1);
                int nr = rs.getInt(2);
                int codComanda = rs.getInt(3);
                String dataComanda = rs.getString(4);
                String client = rs.getString(5);
                String produse = rs.getString(6);
                String adresa = rs.getString(7);
                String localitate = rs.getString(8);
                String codPostal = rs.getString(9);
                String tara = rs.getString(10);
                String telefon = rs.getString(11);
                String email = rs.getString(12);
                String observatii = rs.getString(13);
                int valoareProduse = rs.getInt(14);
                String incasat = rs.getString(15);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat);
                if (checkOrder.getTara().equals("RO") && !checkOrder.getLocalitate().contains("TM")){
                    nationalOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Collections.sort(nationalOrders, Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return nationalOrders;
    }

    public List<DisplayedOrder> displayInternationalOrders(){
        List<DisplayedOrder> internationalOrders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String status = rs.getString(1);
                int nr = rs.getInt(2);
                int codComanda = rs.getInt(3);
                String dataComanda = rs.getString(4);
                String client = rs.getString(5);
                String produse = rs.getString(6);
                String adresa = rs.getString(7);
                String localitate = rs.getString(8);
                String codPostal = rs.getString(9);
                String tara = rs.getString(10);
                String telefon = rs.getString(11);
                String email = rs.getString(12);
                String observatii = rs.getString(13);
                int valoareProduse = rs.getInt(14);
                String incasat = rs.getString(15);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat);
                if (!checkOrder.getTara().equals("RO")){
                    internationalOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Collections.sort(internationalOrders, Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return internationalOrders;
    }

    public DisplayedOrder orderExists(int codComanda){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders WHERE cod_comanda = ?");
            ps.setInt(1,codComanda);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                System.out.println(rs.getInt(2));
                return new DisplayedOrder(rs.getString(1),rs.getInt(2),rs.getInt(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),
                        rs.getString(11),rs.getString(12),rs.getString(13),rs.getInt(14),
                        rs.getString(15));
            }
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
