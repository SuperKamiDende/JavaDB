import java.sql.*;

public class StudentManager {
    private Connection connection;

    // Constructor to establish database connection
    public StudentManager() {
        try {
            String url = "jdbc:postgresql://localhost:5432/HundeyJavaDB";
            String username = "postgres";
            String password = "Lolpy911!";
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves and displays all records from the students table
    public void getAllStudents() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                Date enrollmentDate = resultSet.getDate("enrollment_date");
                System.out.println("Student ID: " + studentId + ", Name: " + firstName + " " + lastName +
                        ", Email: " + email + ", Enrollment Date: " + enrollmentDate);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inserts a new student record into the students table
    public void addStudent(String firstName, String lastName, String email, Date enrollmentDate) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, enrollmentDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Updates the email address for a student with the specified student_id
    public void updateStudentEmail(int studentId, String newEmail) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE students SET email = ? WHERE student_id = ?");
            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Email updated successfully for student ID: " + studentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletes the record of the student with the specified student_id
    public void deleteStudent(int studentId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM students WHERE student_id = ?");
            preparedStatement.setInt(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student with ID " + studentId + " deleted successfully.");
            } else {
                System.out.println("No student found with ID " + studentId);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Video test
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        
        // Test getAllStudents()
        System.out.println("Retrieving all students:");
        manager.getAllStudents();
        
        // Test addStudent()
        manager.addStudent("Jimmy", "Johnson", "jimmy.johnson@example.com", Date.valueOf("2023-09-29"));
        
        // Test updateStudentEmail()
        manager.updateStudentEmail(10, "john.updated@example.com");
        
        // Test deleteStudent()
        manager.deleteStudent(12);
        
        // final check after changes
        manager.getAllStudents();
        
        manager.closeConnection();
    }
}
