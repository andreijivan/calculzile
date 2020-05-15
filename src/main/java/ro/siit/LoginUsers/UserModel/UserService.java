package ro.siit.LoginUsers.UserModel;

import java.sql.*;
import java.util.UUID;


public class UserService {
    private Connection connection;

    public UserService(){
        try{
            Class.forName("org.postgresql.Driver");
             connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public User checkCredentials(String username, String password){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM login WHERE username = ? AND pwd = ?");
            ps.setString(1,username);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new User(UUID.fromString(rs.getObject(1).toString()),rs.getString(2),rs.getString(3));
            }
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
