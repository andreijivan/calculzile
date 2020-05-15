package ro.siit.OrderProcessing;

import ro.siit.OrderDetails.DisplayedOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/poliOrders"})
public class PoliOrdersTable extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<DisplayedOrder> orders = orderService.getOrders();
        req.setAttribute("orders",orders);
        req.getRequestDispatcher("/jsps/OrderTable.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }

}