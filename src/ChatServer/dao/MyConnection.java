package ChatServer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MyConnection {
	private static String URL = "jdbc:postgresql://localhost/tieteReports";
	private static String USER = "postgres";
	private static String PASS = "postgres";	
	
	static Connection getConnection() throws SQLException {
		try {
			Connection c = DriverManager.getConnection(URL, USER, PASS);
			return c;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new SQLException();
		}
	}
}
