package student_management_system;

import java.sql.*;

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/student_management_system";
    private static final String user = "root";
    private static final String password = "Tolibadi@123#";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

