
package k213829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Task4 {
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String dbName = "lab10";
        String dbPass = "";
        String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/"+dbName;

        PreparedStatement pst = null;
        ResultSet rs = null;
        Statement st;
        System.out.println("EMPLOYEES DATA: ");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);
            
            String query = "SELECT * FROM employees";
            st = con.createStatement();
            rs = st.executeQuery(query);
                        
            while(rs.next()){
                System.out.println("Emp ID: " + rs.getInt("empID"));
                System.out.println("Emp Name: " + rs.getString("empName"));
                System.out.println("Emp Age: " + rs.getInt("empAge"));
            }
            
            System.out.println("\nEnter Emp ID to update: ");
            int empID = scan.nextInt();
            System.out.println("\nEnter Emp new Age: ");
            int age = scan.nextInt();
            
            String query2 = "UPDATE employees SET empAge = ? WHERE empID = ?";
            pst = con.prepareStatement(query2);
            
            pst.setInt(1, age);
            pst.setInt(2, empID);
            
            pst.executeUpdate();
            System.out.println("Updated Successfully!");
            
            String query3 = "SELECT * FROM employees";
            st = con.createStatement();
            rs = st.executeQuery(query3);
            System.out.println("UPDATED DATA:");            
            while(rs.next()){
                System.out.println("Emp ID: " + rs.getInt("empID"));
                System.out.println("Emp Name: " + rs.getString("empName"));
                System.out.println("Emp Age: " + rs.getInt("empAge"));
            }
        } catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
