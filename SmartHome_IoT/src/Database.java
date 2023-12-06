import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/SH_Account";
    private static final String USER = "root";
    private static final String PASSWORD = "Hungyeuem123";

    public static Connection getConnection() throws SQLException {
	return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean signUpUser(String firstName, String lastName, String email, String password) {
	String query = "INSERT INTO User (FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?)";

	try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

	    pstmt.setString(1, firstName);
	    pstmt.setString(2, lastName);
	    pstmt.setString(3, email);
	    pstmt.setString(4, password);

	    // Check if user already exists
	    if (accountExist(email)) {
		System.out.println("User already exists.");
		return false;
	    }

	    int result = pstmt.executeUpdate();
	    return result > 0;

	} catch (SQLException e) {
	    System.out.println("SQLException: " + e.getMessage());
	    return false;
	}
    }

    public boolean signUpAdmin(String firstName, String lastName, String email, String password) {
	String query = "INSERT INTO Admin (FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?)";

	try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

	    pstmt.setString(1, firstName);
	    pstmt.setString(2, lastName);
	    pstmt.setString(3, email);
	    pstmt.setString(4, password);

	    // Check if user already exists
	    if (accountExist(email)) {
		System.out.println("User already exists.");
		return false;
	    }

	    int result = pstmt.executeUpdate();
	    return result > 0;

	} catch (SQLException e) {
	    System.out.println("SQLException: " + e.getMessage());
	    return false;
	}
    }

    private boolean accountExist(String email) throws SQLException {
	String query = "SELECT COUNT(*) FROM User WHERE Email = ?";

	try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

	    pstmt.setString(1, email);
	    try (ResultSet rs = pstmt.executeQuery()) {
		if (rs.next()) {
		    return rs.getInt(1) > 0;
		}
	    }
	}
	return false;
    }

    public boolean authenticateUser(String email, String password) {
	String query = "SELECT COUNT(*) FROM User WHERE Email = ? AND Password = ?";

	try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

	    pstmt.setString(1, email);
	    pstmt.setString(2, password);

	    try (ResultSet rs = pstmt.executeQuery()) {
		if (rs.next()) {
		    return rs.getInt(1) > 0;
		}
	    }
	} catch (SQLException e) {
	    System.out.println("SQLException: " + e.getMessage());
	}
	return false;
    }

// 
    public boolean authenticateAdmin(String email, String password) {
	String query = "SELECT COUNT(*) FROM Admin WHERE Email = ? AND Password = ?";

	try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

	    pstmt.setString(1, email);
	    pstmt.setString(2, password);

	    try (ResultSet rs = pstmt.executeQuery()) {
		if (rs.next()) {
		    return rs.getInt(1) > 0;
		}
	    }
	} catch (SQLException e) {
	    System.out.println("SQLException: " + e.getMessage());
	}
	return false;
    }

    public String getFirstNameByEmail(String email) {
	String query = "SELECT FirstName FROM User WHERE Email = ?";
	String firstName = null;

	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		PreparedStatement pstmt = conn.prepareStatement(query)) {

	    pstmt.setString(1, email);

	    try (ResultSet rs = pstmt.executeQuery()) {
		if (rs.next()) {
		    firstName = rs.getString("FirstName");
		}
	    }
	} catch (SQLException e) {
	    System.out.println("SQLException: " + e.getMessage());
	}
	return firstName; // Replace this with the retrieved first name
    }

}
