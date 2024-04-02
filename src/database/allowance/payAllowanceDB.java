package database.allowance;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.model.employee.attendence.bonus.PayAllowance;
import database.conn.DatabaseConn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class payAllowanceDB {
static Connection conn = DatabaseConn.getConnection();
	
	/**
	 * Checks if the Employee id  exists in the datatbase
	 * @param id
	 * @return
	 */
	
	
public static void payallowance(PayAllowance all) throws FileNotFoundException {
		
				try {
					
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO payallowance(empid,allowance,paydate) values(?,?,?)");
					stmt.setString(1, all.getEmpID());
					stmt.setDouble(2, all.getAmount());
					stmt.setString(3, all.getDate());
					
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
