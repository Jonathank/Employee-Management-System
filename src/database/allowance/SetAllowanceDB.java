package database.allowance;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.model.employee.attendence.bonus.SetAllowance;
import database.conn.DatabaseConn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SetAllowanceDB {
static Connection conn = DatabaseConn.getConnection();
	
	/**
	 * Checks if the Employee id  exists in the datatbase
	 * @param id
	 * @return
	 */
	public static boolean isEmpIDExists(String id) {
		
	        try {
	            String checkstmt = "SELECT COUNT(*) FROM bonus  WHERE empID =?";
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
	
public static void setallowance(SetAllowance all) throws FileNotFoundException {
		
				try {
					
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO bonus(empid,department,allowance,category) values(?,?,?,?)");
					stmt.setString(1, all.getEmpid());
					stmt.setString(2, all.getDepart());
					stmt.setDouble(3, all.getAllowance());
					stmt.setString(4, all.getCategory());
					
					//excute
					stmt.execute();
					
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("SAVING RECORD");
					alert.setContentText("RECORD ADDED SUCCESSFULLY");
					alert.showAndWait();
					
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			
	}


}
