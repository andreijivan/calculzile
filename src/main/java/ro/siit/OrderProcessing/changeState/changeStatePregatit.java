package ro.siit.OrderProcessing.changeState;

import ro.siit.OrderDetails.DisplayedOrder;
import ro.siit.OrderProcessing.OrderService;
import ro.siit.OrderProcessing.UpdateOrderTable;

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

@WebServlet(urlPatterns = {"/changeStatePregatit"})
public class changeStatePregatit extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Scanner scanner = new Scanner(test).useDelimiter("[^0-9]+");
        int codComandaChangeState = scanner.nextInt();
        DisplayedOrder finalizedOrder = orderService.orderExists(String.valueOf(codComandaChangeState));
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));

            PreparedStatement ps = connection.prepareStatement
                    ("UPDATE poliorders SET state = 'pregatit' WHERE cod_comanda = ?");
            ps.setInt(1, codComandaChangeState);
            ps.executeUpdate();
            ps.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }

        List<DisplayedOrder> totalOrders = orderService.getAllOrders();
        req.setAttribute("orders",totalOrders);
        req.getRequestDispatcher("/jsps/table.jsp").forward(req,resp);
    }
}

