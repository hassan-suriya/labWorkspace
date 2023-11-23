
package k213829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String dbName = "lab10";
        String dbPass = "";
        String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/"+dbName;
        
        PreparedStatement pst;
        ResultSet rs = null;
        Statement st;
        
        System.out.println("Enter Employee Name: ");
        String name = scan.next();
        System.out.println("Enter Employee age: ");
        int age = scan.nextInt();
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);
            
            String query = "INSERT INTO employees (empName, empAge) VALUES (?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            
            pst.executeUpdate();
            System.out.println("Data Inserted Successfull!");
            
            String query2 = "SELECT * FROM employees";
            st = con.createStatement();
            rs = st.executeQuery(query2);
            
            System.out.println("\nRetreiving Data: ");
            
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
