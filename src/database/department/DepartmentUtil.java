package database.department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.model.department.Department;
import database.conn.DatabaseConn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author KASULE PAUL
 *
 */

public class DepartmentUtil {
	static Connection conn = DatabaseConn.getConnection();
	
	/**
	 * Checks if the Employee id  exists in the datatbase
	 * @param id
	 * @return
	 */
	public static boolean isDepartmentIDExists(String id) {
		
	        try {
	            String checkstmt = "SELECT COUNT(*) FROM department WHERE Department_ID =?";
	            PreparedStatement stmt = conn.prepareStatement(checkstmt);
	            stmt.setString(1, id);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return (count > 0);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }
	
	public static void addDepartment(Department depart) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO department(Department_ID,Name) VALUES(?,?)");
			stmt.setString(1, depart.getDepartID());
			stmt.setString(2,depart.getDepart());
			
			//excute
			stmt.execute();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("SAVING RECORD");
			alert.setContentText("RECORD ADDED SUCCESSFULLY");
			alert.showAndWait();
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void setDepartmentHOD(String empID,String departID) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPADTE department SET HOD=? WHERE Department_ID =? ");
			stmt.setString(1, empID);
			stmt.setString(2, departID);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
