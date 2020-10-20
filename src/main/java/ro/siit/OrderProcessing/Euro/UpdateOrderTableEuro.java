package ro.siit.OrderProcessing.Euro;

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

@WebServlet(urlPatterns = {"/updateOrderTableEuro"})
public class UpdateOrderTableEuro extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Scanner scanner = new Scanner(test).useDelimiter("[^0-9]+");
        int codComandaFinalized = scanner.nextInt();
        List<DisplayedOrder> allOrders = orderService.getAllOrdersEuro();
        DisplayedOrder finalizedOrder = new DisplayedOrder();
        for (DisplayedOrder order: allOrders){
            if (order.getCodComanda() == codComandaFinalized){
                finalizedOrder = order;
            }
        }

        if (finalizedOrder.getStatus().contains("Achitat online CARD")) {
            Connection connection = null;
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
                PreparedStatement qs = connection.prepareStatement
                        ("UPDATE poliorderseuro SET state = 'finalizat' WHERE cod_comanda = ?");
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
            deleteFromPoliOrdersEuro(codComandaFinalized);
        }

        else if (finalizedOrder.getStatus().contains("Achitat online TRANSFER BANCAR")) {
            insertIntoBancaEuro(codComandaFinalized);
        }
        List<DisplayedOrder> totalOrders = orderService.getAllOrdersEuro();
        req.setAttribute("orders", totalOrders);
        req.getRequestDispatcher("/jsps/tableEuro.jsp").forward(req, resp);
    }

    private void insertIntoBancaEuro(int codComandaFinalized) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
             connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement qs = connection.prepareStatement
                    ("UPDATE poliorderseuro SET state = 'finalizat' WHERE cod_comanda = ?");
            qs.setInt(1, codComandaFinalized);
            qs.executeUpdate();
            qs.close();
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO comenzifinalizatebanca SELECT * from poliorderseuro WHERE cod_comanda = ?");
            ps.setInt(1, codComandaFinalized);
            ps.executeUpdate();
            ps.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        deleteFromPoliOrdersEuro(codComandaFinalized);
    }

    static void deleteFromPoliOrdersEuro(int codComandaFinalized) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement
                    ("DELETE FROM poliorderseuro WHERE cod_comanda = ?");
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

