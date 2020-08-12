package ro.siit.OrderProcessing;

import ro.siit.OrderDetails.DisplayedOrder;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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

    public List<DisplayedOrder> displayFinalizedCashOrders() throws ParseException {
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
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-2);
        Date compareDate = cal.getTime();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            for (DisplayedOrder archivedOrder : finalizedCashOrders) {
                Date archiveDate = new SimpleDateFormat("yyyy-MM-dd").parse(archivedOrder.getDataComanda());
                if (archiveDate.compareTo(compareDate) < 0) {
                    PreparedStatement qs = connection.prepareStatement("INSERT INTO arhiva SELECT * from comenzifinalizatecash WHERE cod_comanda = ?");
                    qs.setInt(1, archivedOrder.getCodComanda());
                    qs.executeUpdate();
                    qs.close();
                    PreparedStatement zs = connection.prepareStatement("DELETE FROM comenzifinalizatecash WHERE cod_comanda = ?");
                    zs.setInt(1, archivedOrder.getCodComanda());
                    zs.executeUpdate();
                    zs.close();
                }
            }
        }
        catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        finalizedCashOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return finalizedCashOrders;
    }

    public List<DisplayedOrder> displayFinalizedBankOrders() throws ParseException {
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
            } catch (Exception e) {  /* ignored */  }
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-2);
        Date compareDate = cal.getTime();
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                    for (DisplayedOrder archivedOrder : finalizedBankOrders) {
                        Date archiveDate = new SimpleDateFormat("yyyy-MM-dd").parse(archivedOrder.getDataComanda());
                        if (archiveDate.compareTo(compareDate) < 0) {
                            PreparedStatement qs = connection.prepareStatement("INSERT INTO arhiva SELECT * from comenzifinalizatebanca WHERE cod_comanda = ?");
                            qs.setInt(1, archivedOrder.getCodComanda());
                            qs.executeUpdate();
                            qs.close();
                            PreparedStatement zs = connection.prepareStatement("DELETE FROM comenzifinalizatebanca WHERE cod_comanda = ?");
                            zs.setInt(1, archivedOrder.getCodComanda());
                            zs.executeUpdate();
                            zs.close();
                        }
                    }
                }
                    catch (SQLException | ClassNotFoundException throwable) {
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
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-2);
        Date compareDate = cal.getTime();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            for (DisplayedOrder archivedOrder : finalizedCardOrders) {
                Date archiveDate = new SimpleDateFormat("yyyy-MM-dd").parse(archivedOrder.getDataComanda());
                if (archiveDate.compareTo(compareDate) < 0) {
                    PreparedStatement qs = connection.prepareStatement("INSERT INTO arhiva SELECT * from comenzifinalizatecard WHERE cod_comanda = ?");
                    qs.setInt(1, archivedOrder.getCodComanda());
                    qs.executeUpdate();
                    qs.close();
                    PreparedStatement zs = connection.prepareStatement("DELETE FROM comenzifinalizatecard WHERE cod_comanda = ?");
                    zs.setInt(1, archivedOrder.getCodComanda());
                    zs.executeUpdate();
                    zs.close();
                }
            }
        }
        catch (SQLException | ClassNotFoundException | ParseException throwable) {
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
                String valoareLivrare = rs.getString(17);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare);
                if (checkOrder.getLocalitate().contains("TM")) {
                    localOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare));
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
                String valoareLivrare = rs.getString(17);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare);
                if (checkOrder.getTara().equals("RO") && !checkOrder.getLocalitate().contains("TM")) {
                    nationalOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare));
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
                String valoareLivrare = rs.getString(17);

                DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare);
                if (!checkOrder.getTara().equals("RO")) {
                    internationalOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare));
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

    public DisplayedOrder orderExists(String nameOrCode) {
        if (nameOrCode.matches("[0-9]+")){
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders WHERE cod_comanda = ?");
                ps.setInt(1, Integer.parseInt(nameOrCode));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {

                    return new DisplayedOrder(rs.getString(1), rs.getInt(2), rs.getInt(3),
                            rs.getString(4), rs.getString(5), rs.getString(6),
                            rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                            rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14),
                            rs.getString(15), rs.getString(16), rs.getString(17));
                }
                else{
                    List<DisplayedOrder> totalOrders = new OrderService().getTotalRevenue();
                    for (DisplayedOrder order: totalOrders){
                        if (order.getCodComanda() == Integer.parseInt(nameOrCode)){
                            return order;
                        }
                    }
                }
            } catch (Exception throwable) {
                throwable.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }
            }
        }
        else{
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement qs = connection.prepareStatement("SELECT * FROM poliorders WHERE client LIKE ?");
                qs.setString(1, "%" + nameOrCode + "%");
                ResultSet rs = qs.executeQuery();
                if (rs.next()) {
                    return new DisplayedOrder(rs.getString(1), rs.getInt(2), rs.getInt(3),
                            rs.getString(4), rs.getString(5), rs.getString(6),
                            rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                            rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14),
                            rs.getString(15), rs.getString(16), rs.getString(17));
                }
                else{
                    List<DisplayedOrder> totalOrders = new OrderService().getTotalRevenue();
                    for (DisplayedOrder order: totalOrders){
                        if (order.getClient().contains(nameOrCode)){
                            return order;
                        }
                    }
                }
            } catch (Exception throwable) {
                throwable.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }
            }
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

    public List<DisplayedOrder> getArchive(){
        List<DisplayedOrder> archivedOrders = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM arhiva");
            ResultSet rs = ps.executeQuery();

            prepareDisplayedOrder(archivedOrders, ps, rs);

        } catch (Exception throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        archivedOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return archivedOrders;
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
            String valoareLivrare = rs.getString(17);
            DisplayedOrder checkOrder = new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare);
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
            String valoareLivrare = rs.getString(17);

            finalizedOrders.add(new DisplayedOrder(status, nr, codComanda, dataComanda, client, produse, adresa, localitate, codPostal, tara, telefon, email, observatii, valoareProduse, incasat, state, valoareLivrare));
        }
    }

    public List<DisplayedOrder> getTotalRevenue() {
        List<DisplayedOrder> totalRevenueOrders = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM comenzifinalizatecash");
            displayOrderList(totalRevenueOrders, ps);
            ps.close();
            PreparedStatement qs = connection.prepareStatement("SELECT * FROM comenzifinalizatecard");
            displayOrderList(totalRevenueOrders, qs);
            qs.close();
            PreparedStatement zs = connection.prepareStatement("SELECT * FROM comenzifinalizatebanca");
            displayOrderList(totalRevenueOrders, zs);
            zs.close();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }
        }
        totalRevenueOrders.sort(Comparator.comparingInt(DisplayedOrder::getCodComanda).reversed());
        return totalRevenueOrders;
    }

}
