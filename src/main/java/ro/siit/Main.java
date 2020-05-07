package ro.siit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = {"/mapAge"})
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/enterAge.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String jspDate = req.getParameter("yearOfBirth");
        String newDate;

        try {
            Date date = simpleDateFormat.parse(jspDate);
            newDate = simpleDateFormat.format(date);
            resp.setContentType("application/json");
            resp.getWriter().println("Your date of birth is " + newDate); //just a test line to make sure simpleDateFormat works
            resp.getWriter().println("Total days lived on planet Earth until today: " + MapAge.calculateAgeInDays(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

