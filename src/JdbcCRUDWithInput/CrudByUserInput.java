package JdbcCRUDWithInput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CrudByUserInput {
	    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb2";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "abc123";
	    
	public static void main(String[] args) {
		 try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				 Scanner scanner = new Scanner(System.in)) {

	            while (true) {
	                System.out.println("\n--- CRUD Operations ---");
	                System.out.println("1. Create (Insert)");
	                System.out.println("2. Read (Select)");
	                System.out.println("3. Update");
	                System.out.println("4. Delete");
	                System.out.println("5. Exit");
	                System.out.print("Choose an option: ");
	                int choice = scanner.nextInt();

	                switch (choice) {
	                    case 1:
	                        createUser(connection, scanner);
	                        break;
	                    case 2:
	                        readUsers(connection);
	                        break;
	                    case 3:
	                        updateUser(connection, scanner);
	                        break;
	                    case 4:
	                        deleteUser(connection, scanner);
	                        break;
	                    case 5:
	                        System.out.println("Exiting...");
	                        return;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	 private static void createUser(Connection connection, Scanner scanner) {
		 System.out.println("\n--- Create User ---");
		 System.out.println("Enter Name :");
		 scanner.nextLine(); // Consume newline
		 String name = scanner.nextLine();
		 System.out.println("Enter Email :");
		 String email = scanner.nextLine();
		 System.out.println("Enter age :");
		 int age = scanner.nextInt();
		 
		 String insertQuery = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
	       try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

         preparedStatement.setString(1,name);
         preparedStatement.setString(2,email );
         preparedStatement.setInt(3,age);
         
         int rowsInserted = preparedStatement.executeUpdate();
         System.out.println(rowsInserted + " user(s) added successfully.");
     }   catch (SQLException e) {
         e.printStackTrace();
     }
 }
	 private static void readUsers(Connection connection){
		 System.out.println("\n---Read User---");
		 String selectQuery = "SELECT * FROM users ";
         try(Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(selectQuery)){

         while (resultSet.next()) {
             System.out.println("ID: " + resultSet.getInt("id"));
             System.out.println("Name: " + resultSet.getString("name"));
             System.out.println("Email: " + resultSet.getString("email"));
             System.out.println("Age: " + resultSet.getInt("age"));
             System.out.println("-----");
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
	 private static void updateUser(Connection connection, Scanner scanner){
		 System.out.println("\n---Update User---");
		 System.out.print("Enter user ID to update: ");
	        int id = scanner.nextInt();
	        System.out.print("Enter new email: ");
	        scanner.nextLine(); // Consume newline
	        String newEmail = scanner.nextLine();

	        String query = "UPDATE users SET email = ? WHERE id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, newEmail);
	            preparedStatement.setInt(2, id);

	            int rowsUpdated = preparedStatement.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("User updated successfully.");
	            } else {
	                System.out.println("User with ID " + id + " not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 private static void deleteUser(Connection connection , Scanner scanner){
		 System.out.println("\n---Delete User---");
		 System.out.print("Enter user ID to Delete : ");
	     int id = scanner.nextInt();
		 String deleteQuery = "DELETE FROM users WHERE id = ?";
         try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){

         preparedStatement.setInt(1, id);

         int rowsDeleted = preparedStatement.executeUpdate();
         if (rowsDeleted > 0) {
             System.out.println("User deleted successfully.");
         } else {
             System.out.println("User with ID " + id + " not found.");
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
} 
	 
	 
	 
	 


