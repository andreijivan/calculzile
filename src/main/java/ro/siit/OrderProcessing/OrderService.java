package ro.siit.OrderProcessing;

import ro.siit.OrderDetails.DisplayedOrder;

import java.sql.*;
import java.util.ArrayList;
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

    public List<DisplayedOrder> getOrders(){
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
                orders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public boolean SearchOrder(String username){
       try{
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM login WHERE username = ?");
        ps.setString(1,username);

        ResultSet rs = ps.executeQuery();

           return rs.next();

    } catch (SQLException e) {
        e.printStackTrace();
    }
        return true;
    }

    public List<DisplayedOrder> noVirtualOrders(){
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
                if (!checkOrder.getProduse().contains("Bilet virtual") && checkOrder.getProduse().length()<90){
                    orders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public boolean orderExists(int codComanda){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM login WHERE cod_comanda = ?");
            ps.setInt(1,codComanda);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return true;
            else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
