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

@WebServlet(urlPatterns = {"/revokeFinalizedCardOrder"})
public class RevokeFinalizedCardOrder extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Scanner scanner = new Scanner(test).useDelimiter("[^0-9]+");
        int codComandaRevoked = scanner.nextInt();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO poliorders SELECT * from comenzifinalizatecard WHERE cod_comanda = ?");
            ps.setInt(1, codComandaRevoked);
            ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement
                    ("DELETE FROM comenzifinalizatecard WHERE cod_comanda = ?");
            ps.setInt(1, codComandaRevoked);
            ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        List<DisplayedOrder> totalOrders = orderService.displayFinalizedCardOrders();
        req.setAttribute("orders",totalOrders);
        req.getRequestDispatcher("/jsps/finalizedCardTable.jsp").forward(req,resp);
    }
}

