package ro.siit;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = {"/mapAge"})
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/EnterAge.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String jspDate = req.getParameter("yearOfBirth");
        Date date = null;
        try {
            date = simpleDateFormat.parse(jspDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newDate = simpleDateFormat.format(date);
        long result = MapAge.calculateAgeInDays(date);
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO dummytable (birthday, totaldays) VALUES (?, ?)");
            ps.setString(1, jspDate);
            ps.setInt(2, (int) result);
            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        resp.setContentType("application/json");
        resp.getWriter().println("Your date of birth is " + newDate);
        resp.getWriter().println("Total days lived on planet Earth until today: " + result);

    }
    private static Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }
}

