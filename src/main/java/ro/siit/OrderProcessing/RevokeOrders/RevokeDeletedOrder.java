package ro.siit.OrderProcessing.RevokeOrders;

import ro.siit.OrderDetails.DisplayedOrder;
import ro.siit.OrderProcessing.OrderService;

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

@WebServlet(urlPatterns = {"/revokeDeletedOrder"})
public class RevokeDeletedOrder extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Scanner scanner = new Scanner(test).useDelimiter("[^0-9]+");
        int codComandaRevoked = scanner.nextInt();
        DisplayedOrder searchedOrder = new OrderService().orderExists(String.valueOf(codComandaRevoked));
        Connection connection = null;
        if (searchedOrder.getAdresa().equals(" ")){
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement qs = connection.prepareStatement
                        ("UPDATE comenzianulate SET state = 'finalizat' WHERE cod_comanda = ?");
                qs.setInt(1, codComandaRevoked);
                qs.executeUpdate();
                qs.close();
                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO produsevirtuale SELECT * from comenzianulate WHERE cod_comanda = ?");
                ps.setInt(1, codComandaRevoked);
                ps.executeUpdate();
                ps.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }
            }
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement ps = connection.prepareStatement
                        ("DELETE FROM comenzianulate WHERE cod_comanda = ?");
                ps.setInt(1, codComandaRevoked);
                ps.executeUpdate();
                ps.close();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }
            }
        }
        else {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement qs = connection.prepareStatement
                        ("UPDATE comenzianulate SET state = 'pending' WHERE cod_comanda = ?");
                qs.setInt(1, codComandaRevoked);
                qs.executeUpdate();
                qs.close();
                PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO poliorders SELECT * from comenzianulate WHERE cod_comanda = ?");
                ps.setInt(1, codComandaRevoked);
                ps.executeUpdate();
                ps.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }
            }
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement ps = connection.prepareStatement
                        ("DELETE FROM comenzianulate WHERE cod_comanda = ?");
                ps.setInt(1, codComandaRevoked);
                ps.executeUpdate();
                ps.close();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }
            }
        }
        List<DisplayedOrder> totalOrders = orderService.getDeletedOrders();
        req.setAttribute("orders",totalOrders);
        req.getRequestDispatcher("/jsps/deletedTable.jsp").forward(req,resp);
    }
}

