package ro.siit.OrderProcessing.FinalizedOrdersSort;

import ro.siit.OrderDetails.DisplayedOrder;
import ro.siit.OrderProcessing.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/showTotalRevenue"})
public class ShowTotalRevenue extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<DisplayedOrder> totalOrders = orderService.getTotalRevenue();
        req.setAttribute("orders", totalOrders);
        req.setAttribute("noOfOrders", totalOrders.size());
        req.getRequestDispatcher("/jsps/totalRevenueTable.jsp").forward(req, resp);

    }

}

