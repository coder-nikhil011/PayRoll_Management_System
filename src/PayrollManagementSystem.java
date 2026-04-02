import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PayrollManagementSystem {

    static final String DB_URL = "jdbc:mysql://localhost:3306/payroll_db";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            System.out.println("Connected to the database successfully!");

            while (true) {
                System.out.println("\nPayroll Management System");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch(choice) {
                    case 1:
                        addEmployee(connection, scanner);
                        break;
                    case 2:
                        viewEmployees(connection);
                        break;
                    case 3:
                        updateEmployeeSalary(connection, scanner);
                        break;
                    case 4:
                        deleteEmployee(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting the system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addEmployee(Connection connection, Scanner scanner) throws SQLException {

        System.out.print("Enter employee name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();

        String query = "INSERT INTO employees (name, salary) VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        ps.setDouble(2, salary);

        ps.executeUpdate();
        System.out.println("Employee added!");
    }

    private static void viewEmployees(Connection connection) throws SQLException {

        String query = "SELECT * FROM employees";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            System.out.println(rs.getInt("id") + " " +
                               rs.getString("name") + " " +
                               rs.getDouble("salary"));
        }
    }

    private static void updateEmployeeSalary(Connection connection, Scanner scanner) throws SQLException {

        System.out.print("Enter ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter new salary: ");
        double newSalary = scanner.nextDouble();

        String query = "UPDATE employees SET salary=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setDouble(1, newSalary);
        ps.setInt(2, id);

        ps.executeUpdate();
        System.out.println("Updated!");
    }

    private static void deleteEmployee(Connection connection, Scanner scanner) throws SQLException {

        System.out.print("Enter ID: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM employees WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, id);
        ps.executeUpdate();

        System.out.println("Deleted!");
    }
}