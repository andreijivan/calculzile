package ro.siit.OrderProcessing;

import ro.siit.OrderDetails.DisplayedOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderService {

    private Connection connection;

    public List<DisplayedOrder> getAllOrders() {
        List<DisplayedOrder> orders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();

            prepareDisplayedOrder(orders, ps, rs);

        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        orders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return orders;
    }

    public List<DisplayedOrder> displayFinalizedCashOrders() {
        List<DisplayedOrder> finalizedCashOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM comenzifinalizatecash");
            displayOrderList(finalizedCashOrders, ps);
            ps.close();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        finalizedCashOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return finalizedCashOrders;
    }

    public List<DisplayedOrder> displayFinalizedBankOrders() {
        List<DisplayedOrder> finalizedBankOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM comenzifinalizatebanca");

            displayOrderList(finalizedBankOrders, ps);

            ps.close();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        finalizedBankOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return finalizedBankOrders;
    }

    public List<DisplayedOrder> displayFinalizedCardOrders() {
        List<DisplayedOrder> finalizedCardOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM comenzifinalizatecard");
            displayOrderList(finalizedCardOrders, ps);
            ps.close();
        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        finalizedCardOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return finalizedCardOrders;
    }

    public List<DisplayedOrder> displayVirtualOrders() {
        List<DisplayedOrder> virtualOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM produsevirtuale");
            displayOrderList(virtualOrders, ps);
            ps.close();
        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        virtualOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return virtualOrders;
    }

    public List<DisplayedOrder> displayLocalOrders() {
        List<DisplayedOrder> localOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                String state = rs.getString(16);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state);
                if (checkOrder.getLocalitate().contains("TM")) {
                    localOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state));
                }
            }
            ps.close();
        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        localOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return localOrders;
    }

    public List<DisplayedOrder> displayNationalOrders() {
        List<DisplayedOrder> nationalOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                String state = rs.getString(16);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state);
                if (checkOrder.getTara().equals("RO") && !checkOrder.getLocalitate().contains("TM")) {
                    nationalOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state));
                }
            }
            ps.close();
        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        nationalOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return nationalOrders;
    }

    public List<DisplayedOrder> displayInternationalOrders() {
        List<DisplayedOrder> internationalOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                String state = rs.getString(16);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state);
                if (!checkOrder.getTara().equals("RO")) {
                    internationalOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state));
                }
            }
            ps.close();
        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        internationalOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return internationalOrders;
    }

    public DisplayedOrder orderExists(int codComanda) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders WHERE cod_comanda = ?");
            ps.setInt(1, codComanda);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // System.out.println(rs.getInt(2));

                return new DisplayedOrder(rs.getString(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14),
                        rs.getString(15), rs.getString(16));
            }
        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        return null;
    }

    public List<DisplayedOrder> getDeletedOrders() {
        List<DisplayedOrder> deletedOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM comenzianulate");
            ResultSet rs = ps.executeQuery();

            prepareDisplayedOrder(deletedOrders, ps, rs);

        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        deletedOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return deletedOrders;
    }

    private void prepareDisplayedOrder(List<DisplayedOrder> orders, PreparedStatement ps, ResultSet rs) throws SQLException {
        while (rs.next()) {
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
            String state = rs.getString(16);
            DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state);
            orders.add(checkOrder);
        }
        ps.close();
    }

    private void displayOrderList(List<DisplayedOrder> finalizedOrders, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
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
            String state = rs.getString(16);

            finalizedOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state));
        }
    }

}
