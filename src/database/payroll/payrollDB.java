package database.payroll;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import data.model.payRoll.PayRoll;
import database.conn.DatabaseConn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class payrollDB {
static Connection conn = DatabaseConn.getConnection();
	
	/**
	 * Checks if the Employee id  exists in the datatbase
	 * @param id
	 * @return
	 */
	public static boolean isEmpIDExists(String id) {
		
	        try {
	            String checkstmt = "SELECT COUNT(*) FROM payroll WHERE empID =?";
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
	
public static void makepayment(PayRoll pay) throws FileNotFoundException {
		
				try {
					
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO payroll(empid,accountno,grosssalary,nssf_pay,payasyou_earn,basicsalary,paymonth,paydate) values(?,?,?,?,?,?,?,?)");
					stmt.setString(1, pay.getEmpId());
					stmt.setString(2, pay.getAccountNo());
					stmt.setDouble(3, pay.getGrossSalary());
					stmt.setDouble(4, pay.getNssf());
					stmt.setDouble(5, pay.getPayAsYouEarn());
					stmt.setDouble(6, pay.getNetSalary());
					stmt.setString(7, pay.getPaymonth());
					stmt.setString(8, pay.getPaydate());
					
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
