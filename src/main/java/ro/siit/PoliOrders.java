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

@WebServlet(urlPatterns = {"/poliOrders"})
public class PoliOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/OrderTable.jsp").forward(req,resp);
    }

/*    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String Status = ""; int Nr = 0; int codComanda =0; String Data = ""; String Client = ""; String Produse = "";
        String Adresa = ""; String Localitate = ""; String codPostal = ""; String Tara = ""; String nrTelefon = "";
        String email = ""; String Observatii = ""; int valoareProduse = 0; String incasat = "";

        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO poliOrders (Status, Nr, Cod comanda, Data, Client, Produse, Adresa, Localitate, Cod Postal, Tara, Numar Telefon, Email, Observatii, Valoare produse, Incasat) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, Status);
            ps.setInt(2, Nr);
            ps.setInt(3, codComanda);
            ps.setString(4,Data);
            ps.setString(5,Client);
            ps.setString(6,Produse);
            ps.setString(7,Adresa);
            ps.setString(8,Localitate);
            ps.setString(9,codPostal);
            ps.setString(10,Tara);
            ps.setString(11,nrTelefon);
            ps.setString(12,email);
            ps.setString(13,Observatii);
            ps.setInt(14,valoareProduse);
            ps.setString(15,incasat);

            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    private static Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }
}
