
package k213829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String dbName = "lab10";
        String dbPass = "";
        String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/"+dbName;
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        Statement st;
        
        System.out.println("Students Data: ");
         try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String query3 = "SELECT * FROM student";
            st = con.createStatement();
            rs = st.executeQuery(query3);
            while(rs.next()){
                System.out.println("Student ID: " + rs.getInt("id"));
                System.out.println("Student Name: " + rs.getString("name"));
                System.out.println("Student Age: " + rs.getInt("age"));
                System.out.println("\n");
            }
            
            System.out.println("Enter Student ID to delete: ");
            int id = scan.nextInt();
            
            String query = "DELETE FROM student where id = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Data Deleted Successfully!\n");
            System.out.println("After Deleting: \n");
            
            String query2 = "SELECT * FROM student";
            st = con.createStatement();
            rs = st.executeQuery(query2);
            while(rs.next()){
                System.out.println("Student ID: " + rs.getInt("id"));
                System.out.println("Student Name: " + rs.getString("name"));
                System.out.println("Student Age: " + rs.getInt("age"));
                System.out.println("\n");
            }
        } catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
