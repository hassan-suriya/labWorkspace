
package k213829;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class Task7 extends Application {
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement st;

    String dbName = "lab10";
    String dbPass = "";
    String dbUser = "root";
    String url = "jdbc:mysql://localhost:3306/"+dbName;

    TableView<Employee> tableView;
    ObservableList<Employee> data;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX DB APP");

        tableView = new TableView<>();
        data = FXCollections.observableArrayList();

        TableColumn<Employee, Integer> empIDCol = new TableColumn<>("Employee ID");
        empIDCol.setCellValueFactory(new PropertyValueFactory<>("empID"));

        TableColumn<Employee, String> empNameCol = new TableColumn<>("Employee Name");
        empNameCol.setCellValueFactory(new PropertyValueFactory<>("empName"));

        TableColumn<Employee, Integer> empAgeCol = new TableColumn<>("Employee Age");
        empAgeCol.setCellValueFactory(new PropertyValueFactory<>("empAge"));

        tableView.getColumns().addAll(empIDCol, empNameCol, empAgeCol);

        TextField empNameField = new TextField();
        empNameField.setPromptText("Employee Name");

        TextField empAgeField = new TextField();
        empAgeField.setPromptText("Employee Age");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addRecord(empNameField.getText(), Integer.parseInt(empAgeField.getText())));

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateRecord(empNameField.getText(), Integer.parseInt(empAgeField.getText())));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteRecord());

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        grid.add(tableView, 0, 0, 3, 1);
        grid.add(empNameField, 0, 1);
        grid.add(empAgeField, 1, 1);
        grid.add(addButton, 0, 2);
        grid.add(updateButton, 1, 2);
        grid.add(deleteButton, 2, 2);
        
        loadData();

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void loadData() {
        data.clear();
        
        try{
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "SELECT * FROM employees";
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                int empID = rs.getInt("empID");
                String empName = rs.getString("empName");
                int empAge = rs.getInt("empAge");
                data.add(new Employee(empID, empName, empAge));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
            
    } 

    private void addRecord(String empName, int empAge) {
        try {
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "INSERT INTO employees (empName, empAge) VALUES (?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, empName);
            pst.setInt(2, empAge);
            pst.executeUpdate();
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRecord(String empName, int empAge) {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                Connection con = DriverManager.getConnection(url, dbUser, dbPass);
                String query = "UPDATE employees SET empName=?, empAge=? WHERE empID=?";
                pst = con.prepareStatement(query);
                pst.setString(1, empName);
                pst.setInt(2, empAge);
                pst.setInt(3, selectedEmployee.getEmpID());
                pst.executeUpdate();
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Record Selected", "Please select a record to update.");
        }
    }

    private void deleteRecord() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                Connection con = DriverManager.getConnection(url, dbUser, dbPass);
                String query = "DELETE FROM employees WHERE empID=?";
                pst = con.prepareStatement(query);
                pst.setInt(1, selectedEmployee.getEmpID());
                pst.executeUpdate();
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Record Selected", "Please select a record to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Employee {
        private final SimpleIntegerProperty empID;
        private final SimpleStringProperty empName;
        private final SimpleIntegerProperty empAge;

        public Employee(int empID, String empName, int empAge) {
            this.empID = new SimpleIntegerProperty(empID);
            this.empName = new SimpleStringProperty(empName);
            this.empAge = new SimpleIntegerProperty(empAge);
        }

        public int getEmpID() {
            return empID.get();
        }

        public String getEmpName() {
            return empName.get();
        }

        public int getEmpAge() {
            return empAge.get();
        }
    }
}

