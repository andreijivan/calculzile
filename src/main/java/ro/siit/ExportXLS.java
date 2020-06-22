package ro.siit;

import ro.siit.OrderDetails.DisplayedOrder;
import ro.siit.OrderProcessing.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//work in progress
@WebServlet(urlPatterns = {"/exportXLS"})

public class ExportXLS extends HttpServlet {
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*String action = req.getParameter("action");
        if ("download".equals(action)) {
            String currentURL = req.getParameter("currentURL");
            System.out.println(currentURL);
            byte[] bits = "hello".getBytes();
            resp.setContentType("text/plain");
            resp.setHeader("Content-disposition", "attachment; filename=sample.txt");
            resp.getOutputStream().write(bits);
        }*/
        String currentURL = req.getParameter("currentURL");
        System.out.println(currentURL);
        switch (currentURL){
            case "/showAllOrders":
                List<DisplayedOrder> totalOrders = orderService.getAllOrders();
                req.setAttribute("orders", totalOrders);
                req.setAttribute("noOfOrders", totalOrders.size());
        }

    }
}
