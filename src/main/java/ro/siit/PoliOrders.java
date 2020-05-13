package ro.siit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = {"/poliOrders"})
public class PoliOrders extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/OrderTable.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM poliorders ");

            ps.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

  /*  private static Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }*/
}
