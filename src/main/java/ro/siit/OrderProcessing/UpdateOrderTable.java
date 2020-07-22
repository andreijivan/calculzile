package ro.siit.OrderProcessing;

import ro.siit.OrderDetails.DisplayedOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/updateOrderTable"})
public class UpdateOrderTable extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //System.out.println(test);
        Scanner scanner = new Scanner(test).useDelimiter("[^0-9]+");
        int codComandaFinalized = scanner.nextInt();
        DisplayedOrder finalizedOrder = orderService.orderExists(String.valueOf(codComandaFinalized));

        if (finalizedOrder.getStatus().contains("Achitat online CARD")) {
            Connection connection = null;
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement qs = connection.prepareStatement
                        ("UPDATE poliorders SET state = 'finalizat' WHERE cod_comanda = ?");
                qs.setInt(1, codComandaFinalized);
                qs.executeUpdate();
                qs.close();

                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO comenzifinalizatecard SELECT * from poliorders WHERE cod_comanda = ?");
                ps.setInt(1, codComandaFinalized);
                ps.executeUpdate();
                ps.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }finally {
                try { connection.close(); } catch (Exception e) { /* ignored */ }
            }
            deleteFromPoliOrders(codComandaFinalized);

        } else if (finalizedOrder.getStatus().contains("Plata ramburs. Livrare curier")) {
            insertIntoBanca(codComandaFinalized);

        } else if (finalizedOrder.getStatus().contains("Plata ramburs. Ridicare personala")) {
            Connection connection = null;
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement qs = connection.prepareStatement
                        ("UPDATE poliorders SET state = 'finalizat' WHERE cod_comanda = ?");
                qs.setInt(1, codComandaFinalized);
                qs.executeUpdate();
                qs.close();
                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO comenzifinalizatecash SELECT * from poliorders WHERE cod_comanda = ?");
                ps.setInt(1, codComandaFinalized);
                ps.executeUpdate();
                ps.close();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }finally {
                try { connection.close(); } catch (Exception e) { /* ignored */ }
            }
            deleteFromPoliOrders(codComandaFinalized);
        }

        else if (finalizedOrder.getStatus().contains("Achitat online TRANSFER BANCAR")) {
            insertIntoBanca(codComandaFinalized);

        }
        List<DisplayedOrder> totalOrders = orderService.getAllOrders();
        req.setAttribute("orders", totalOrders);
        req.getRequestDispatcher("/jsps/table.jsp").forward(req, resp);
    }

    private void insertIntoBanca(int codComandaFinalized) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
             connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement qs = connection.prepareStatement
                    ("UPDATE poliorders SET state = 'finalizat' WHERE cod_comanda = ?");
            qs.setInt(1, codComandaFinalized);
            qs.executeUpdate();
            qs.close();
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO comenzifinalizatebanca SELECT * from poliorders WHERE cod_comanda = ?");
            ps.setInt(1, codComandaFinalized);
            ps.executeUpdate();
            ps.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        deleteFromPoliOrders(codComandaFinalized);
    }

    static void deleteFromPoliOrders(int codComandaFinalized) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement
                    ("DELETE FROM poliorders WHERE cod_comanda = ?");
            ps.setInt(1, codComandaFinalized);
            ps.executeUpdate();
            ps.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
    }
}

