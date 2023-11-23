
package k213829;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Task8 {
    static String url = "jdbc:mysql://localhost:3306/lab10";
    static String dbUser = "root";
    static String dbPass = "";

    static List<Employee> allData;
    static int currentRecordIndex;

    static JLabel currentRecordLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Paginated View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> showPreviousRecord());

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> showNextRecord());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        currentRecordLabel = new JLabel("Record: 1");

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(currentRecordLabel, BorderLayout.NORTH);

        loadData();
        showRecord(1);

        frame.setVisible(true);
    }

    private static void loadData() {
        allData = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPass);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM employees";
            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    int empID = rs.getInt("empID");
                    String empName = rs.getString("empName");
                    int empAge = rs.getInt("empAge");
                    allData.add(new Employee(empID, empName, empAge));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showRecord(int recordIndex) {
        if (recordIndex > 0 && recordIndex <= allData.size()) {
            currentRecordIndex = recordIndex - 1;
            currentRecordLabel.setText("Record: " + (currentRecordIndex + 1) + " / " + allData.size() +
                    " - " + allData.get(currentRecordIndex).toString());
        }
    }

    private static void showPreviousRecord() {
        if (currentRecordIndex > 0) {
            showRecord(currentRecordIndex);
        }
    }

    private static void showNextRecord() {
        if (currentRecordIndex < allData.size() - 1) {
            showRecord(currentRecordIndex + 2);
        }
    }

    public static class Employee {
        private final int empID;
        private final String empName;
        private final int empAge;

        public Employee(int empID, String empName, int empAge) {
            this.empID = empID;
            this.empName = empName;
            this.empAge = empAge;
        }

        @Override
        public String toString() {
            return "Employee ID: " + empID + ", Name: " + empName + ", Age: " + empAge;
        }
    }
}


