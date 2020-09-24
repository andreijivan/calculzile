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

@WebServlet(urlPatterns = {"/deleteOrder"})
public class DeleteOrder extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Scanner scanner = new Scanner(test).useDelimiter("[^0-9]+");
        int codComandaDelete = scanner.nextInt();
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));

            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO comenzianulate SELECT * from poliorders WHERE cod_comanda = ?");
            ps.setInt(1, codComandaDelete);
            ps.executeUpdate();
            ps.close();
            PreparedStatement qs = connection.prepareStatement
                    ("UPDATE comenzianulate SET state = 'anulat' WHERE cod_comanda = ?");
            qs.setInt(1, codComandaDelete);
            qs.executeUpdate();
            qs.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        UpdateOrderTable.deleteFromPoliOrders(codComandaDelete);
        List<DisplayedOrder> totalOrders = orderService.getAllOrders();
        req.setAttribute("orders",totalOrders);
        req.getRequestDispatcher("/jsps/table.jsp").forward(req,resp);
    }
}

