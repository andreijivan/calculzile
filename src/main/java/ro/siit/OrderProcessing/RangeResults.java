package ro.siit.OrderProcessing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@WebServlet(urlPatterns = {"/rangeResults"})
public class RangeResults extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

     //   System.out.println(req.getParameter("startDate"));
      //  System.out.println(req.getParameter("endDate"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Map<String, Integer> results = new OrderService().centralizedResults(simpleDateFormat.parse(req.getParameter("startDate")),simpleDateFormat.parse(req.getParameter("endDate")));
           /* for(DisplayedOrder order:results){
                System.out.println(order.getCodComanda() + "||" + order.getDataComanda());
            }*/
            req.setAttribute("results", results);
            results.forEach(req::setAttribute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/jsps/centralizatorTable.jsp").forward(req, resp);

    }

}

