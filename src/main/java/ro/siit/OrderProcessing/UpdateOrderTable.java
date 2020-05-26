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

@WebServlet(urlPatterns = {"/updateOrderTables"})
public class UpdateOrderTable extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(test);
        Scanner scanner = new Scanner(test).useDelimiter("[^0-9]+");
        int codComandaFinalized = scanner.nextInt();
        System.out.println(codComandaFinalized);
        DisplayedOrder finalizedOrder = orderService.orderExists(codComandaFinalized);

        try {
            //sa iau comanda din poliorders
            //sa o copiez in comenzifinalizate
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));

            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO comenzifinalizate SELECT * from poliorders WHERE cod_comanda = ?");
            ps.setInt(1, codComandaFinalized);
            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try {
            //sa actualizez show all orders
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement
                    ("DELETE FROM poliorders WHERE cod_comanda = ?");
            ps.setInt(1, codComandaFinalized);
            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        List<DisplayedOrder> totalOrders = orderService.getAllOrders();
        req.setAttribute("orders",totalOrders);
        req.getRequestDispatcher("/jsps/table.jsp").forward(req,resp);
    }
}

