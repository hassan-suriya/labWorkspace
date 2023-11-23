
package k213829;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Task1 {
    public static void main(String[] args) {
        String dbName = "lab10";
        String dbPass = "";
        String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/"+dbName;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);
            if (con == null){
                System.out.println("Connection Failure!");
            }
            else{
                System.out.println("Connection Established Successfully!");
            }
        } catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
