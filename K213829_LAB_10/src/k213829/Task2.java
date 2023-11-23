
package k213829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String dbName = "lab10";
        String dbPass = "";
        String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/"+dbName;
        
        PreparedStatement pst;
        
        System.out.println("Enter Name: ");
        String name = scan.next();
        System.out.println("Enter age: ");
        int age = scan.nextInt();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);
            
            String query = "INSERT INTO student (name, age) VALUES (?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            
            pst.executeUpdate();
            System.out.println("Data Inserted Successfull!");
            
        } catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
