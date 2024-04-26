package database.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author JONATHAN
 *
 */

public class DatabaseConn {
private static Connection con = null;
	
	static {
		String url = "jdbc:mysql://localhost:3306/employee_managementsystem";
		String user = "root";
		String pass ="";
		
		try {
			
			con = DriverManager.getConnection(url, user, pass);
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Returns the database connection object
	public static Connection getConnection() {
		return con;
	}


}
