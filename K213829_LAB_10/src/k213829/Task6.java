
package k213829;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Task6 {
    public static void main(String[] args) {
        Connection connection = null;
        String dbName = "lab10";
        String dbPass = "";
        String dbUser = "root";
        String url = "jdbc:mysql://localhost:3306/"+dbName;
        PreparedStatement insertStatement = null;
        PreparedStatement updateStatement = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = DriverManager.getConnection(url, dbUser, dbPass);

            connection.setAutoCommit(false);

            String insertQuery = "INSERT INTO employees (empName, empAge) VALUES (?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, "John Doe");
            insertStatement.setInt(2, 12);
            insertStatement.executeUpdate();
            
            System.out.println("Inserted Successfully!");

            String updateQuery = "UPDATE employees SET empAge = ? WHERE empID = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, 20);
            updateStatement.setInt(2, 1);
            updateStatement.executeUpdate();
            
            System.out.println("Updated Successfully!");

            String deleteQuery = "DELETE FROM employees WHERE empID = ?";
            deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, 1);
            deleteStatement.executeUpdate();
            
            System.out.println("Deleted Successfully!");
            
            connection.commit();
            System.out.println("Transaction committed successfully!");

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    System.out.println("Transaction rolled back!");
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            try {
                if (insertStatement != null) {
                    insertStatement.close();
                }
                if (updateStatement != null) {
                    updateStatement.close();
                }
                if (deleteStatement != null) {
                    deleteStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }
}
