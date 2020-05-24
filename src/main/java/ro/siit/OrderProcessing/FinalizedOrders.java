package ro.siit.OrderProcessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.siit.OrderDetails.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/finalizeOrders"})
public class FinalizedOrders extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println(test);
        //sa parsez test sa scot cod comanda
        //sa iau comanda din poliorders
        //sa o copiez in comenzifinalizate
        //sa actualizez show all orders
        // sa gasesec o metoda mai buna de a sorta comenzile virtuale
    }
}
