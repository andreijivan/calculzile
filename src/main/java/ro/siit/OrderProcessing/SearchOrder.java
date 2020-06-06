package ro.siit.OrderProcessing;

import ro.siit.OrderDetails.DisplayedOrder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/searchOrder"})
public class SearchOrder extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchValue = req.getParameter("searchValue");
        //System.out.println("Search value is " + searchValue);
        List<DisplayedOrder> foundOrder = new ArrayList<>();
        DisplayedOrder searchedOrder = orderService.orderExists(Integer.parseInt(searchValue));
        if ( searchedOrder != null){
            foundOrder.add(searchedOrder);
            req.setAttribute("orders",foundOrder);
            req.getRequestDispatcher("/jsps/table.jsp").forward(req,resp);
        }
        else {
            PrintWriter writer = resp.getWriter();
            writer.print("NOT_FOUND");
            writer.flush();
        }
    }

}
