package ro.siit.OrderProcessing.ModifyOrder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.siit.OrderDetails.DisplayedOrder;
import ro.siit.OrderProcessing.Euro.ModifyOrderEuro;
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


@WebServlet(urlPatterns = {"/modifyOrder"})
public class ModifyOrder extends HttpServlet {

    OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String oldOrderJSON = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        DisplayedOrder oldOrder = new DisplayedOrder();
        Scanner scanner = new Scanner(oldOrderJSON).useDelimiter("[^0-9]+");
        int codComandaModify = scanner.nextInt();
        List<DisplayedOrder> allOrders = orderService.getAllOrders();
        for (DisplayedOrder order: allOrders){
            if (order.getCodComanda() == codComandaModify){
                oldOrder = order;
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        DisplayedOrder editedOrder = objectMapper.readValue(oldOrderJSON, DisplayedOrder.class);
        editedOrder.setState(oldOrder.getState());

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));

            PreparedStatement ps = connection.prepareStatement
                    ("DELETE FROM poliorders WHERE cod_comanda = ?");
            ps.setInt(1, oldOrder.getCodComanda());
            ps.executeUpdate();
            ps.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement qs = connection.prepareStatement
                    ("INSERT INTO poliorders (status, nr, cod_comanda, data_comanda, client, produse, adresa, localitate, cod_postal, tara, telefon, email, observatii, valoare_produse, incasat, state, cost_transport) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ModifyOrderEuro.insertDBEuro(editedOrder, qs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                /* ignored */
            }
            }
                List<DisplayedOrder> totalOrders = orderService.getAllOrders();
                req.setAttribute("orders", totalOrders);
                req.getRequestDispatcher("/jsps/table.jsp").forward(req, resp);
            }
        }
