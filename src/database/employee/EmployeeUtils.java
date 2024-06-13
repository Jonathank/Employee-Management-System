package database.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import data.model.employee.Employee;
import data.model.employee.attendence.EmployeeAttendance;
import database.conn.DatabaseConn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * 
<<<<<<< HEAD
 * @author JONATHAN K
=======
 * @author JONATHAN
>>>>>>> 9ff76d63643f785ae0d98cbbe55017e061751150
 *
 */

public class EmployeeUtils {
	static Connection conn = DatabaseConn.getConnection();
	
	/**
	 * Returns the auto id's for the Employee that was created last
	 * @return id numb
	 */
	public static int getLastEmployeeID() {
		String sql ="SELECT * FROM auto_id ORDER BY ID_NO DESC LIMIT 1";
		int id = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			//Checks if the table is empty
			if(rs.next() == false) {
				id =99;
				
			}
			else {
				 id =rs.getInt(1);
				 
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		id++; //increment by 1
		
		return id;	
	}
	
	/**
	 * Checks if the Employee id  exists in the datatbase
	 * @param id
	 * @return
	 */
	public static boolean isEmployeeIDExists(String id) {
		
	        try {
	            String checkstmt = "SELECT COUNT(*) FROM employee WHERE Employee_ID =?";
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
	
	/**
	 * Saves the last id in the database
	 * @param id
	 */
	public static void saveId(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO auto_id (ID_NO) VALUES(?)");
			stmt.setInt(1, id);
			//execute
			stmt.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * Adds new Employee into the system
	 * @param emp
	 * @param image
	 * @throws FileNotFoundException 
	 */
	public static void addEmployee(Employee emp, File image) throws FileNotFoundException {
		
		
		//Get the system date and Time
				java.util.Date utilDate = new Date();
				//Convert it to java.sql.Date
				java.sql.Date date = new java.sql.Date(utilDate.getTime());
		
				try {
					
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO employee(employee_ID,FirstName,GivenName,OtherName,Gender,Birth_date,Address,Contact, email,Department,jobTitle,Date_in,Salary,status,photo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					stmt.setString(1, emp.getEmpID());
					stmt.setString(2, emp.getSurName());
					stmt.setString(3, emp.getGivenName());
					stmt.setString(4, emp.getOtherName());
					stmt.setString(5, emp.getGender());
					stmt.setString(6, emp.getDOB());
					stmt.setString(7, emp.getAddress());
					stmt.setString(8, emp.getContact());
					stmt.setString(9, emp.getEmail());
					stmt.setString(10, emp.getDepartment());
					stmt.setString(11, emp.getJobTitle());
					stmt.setDate(12, date);
					stmt.setDouble(13, emp.getGrossSalary());
					stmt.setString(14, emp.getStatus());
					
					//FileInput Stream
					FileInputStream fis = new FileInputStream(image);
					
					stmt.setBinaryStream(15, fis, (int)image.length());
				
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
	//delete button
public static void deleteEmployee(String empid) {
		
		try {
			String SQL = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, empid);
			pstmt.execute();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("REMOVING RECORD");
			alert.setContentText("RECORD REMOVED SUCCESSFULLY");
			alert.showAndWait();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//updates data
public static void updateEmployee(Employee emp, File myfile) throws FileNotFoundException {
	
	try {
		
		String SQL = "UPDATE EMPLOYEE SET firstname =?,Givenname =?,othername =?,gender=?,birth_date=?,address=?,contact=?,email=?,department=?,jobtitle=?,date_in=?,salary=?,status=? WHERE employee_id=?";
		PreparedStatement stmt = conn.prepareStatement(SQL);
		
		stmt.setString(1, emp.getSurName());
		stmt.setString(2, emp.getGivenName());
		stmt.setString(3, emp.getOtherName());
		stmt.setString(4, emp.getGender());
		stmt.setString(5, emp.getDOB());
		stmt.setString(6, emp.getAddress());
		stmt.setString(7, emp.getContact());
		stmt.setString(8, emp.getEmail());
		stmt.setString(9, emp.getDepartment());
		stmt.setString(10, emp.getJobTitle());
		stmt.setDate(11, (java.sql.Date) emp.getStartDate());
		stmt.setDouble(12, emp.getGrossSalary());
		stmt.setString(13, emp.getStatus());
		
		//FileInputStream inputstream = new FileInputStream(myfile);
		//stmt.setBinaryStream(14, inputstream,(int)myfile.length());
		stmt.setString(14, emp.getEmpID());
		
		stmt.executeUpdate();
	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("UPDATING RECORD");
		alert.setContentText("RECORD UPDATED SUCCESSFULLY");
		alert.showAndWait();
		
		
	}catch(SQLException e) {
		
	}
}

	public static void setEmployeeAllowances(String empID, String depart,double amount,String category) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO bouns (EmpID,department,allowance,category) VALUES (?,?,?,?)");
			stmt.setString(1,empID );
			stmt.setString(2, depart);
			stmt.setDouble(3, amount);
			stmt.setString(4, category);
			
			
			//excute
			stmt.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void payEmployeeAllowances(String empID,double amount) {
		try {
			//Get the system date and Time
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String dateT = dtf.format(now);
			
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO payallowance(EmpID,allowance,payDate) VALUES (?,?,?)");
			stmt.setString(1, empID);
			stmt.setDouble(2, amount);
			stmt.setString(3, dateT);
			
			//Excute
			stmt.execute();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//checks id in the bankdetails table
	public static boolean IDExists(String id) {
		
        try {
            String checkstmt = "SELECT COUNT(*) FROM bankdetails WHERE EmpID =?";
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
	
	
	public static void setEmployeeBankDetails(BankDetails bank) {
		try {
			PreparedStatement stmt =conn.prepareStatement("INSERT INTO bankdetails (EmpID,accountNo,accountName)  VALUES(?,?,?)");
			stmt.setString(1, bank.getEmpID());
			stmt.setString(2, bank.getAccountNum());
			stmt.setString(3, bank.getAccountName());
			//excute
			stmt.execute();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("SAVING RECORD");
			alert.setContentText("RECORD ADDED SUCCESSFULLY");
			alert.showAndWait();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setEmployeeTimeIn(EmployeeAttendance attend) {
		try {
		
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO attendance(EmpID,Department,Time_in,Date) VALUES(?,?,?,?) ");

			stmt.setString(1, attend.getEmpID());
			stmt.setString(2, attend.getDepartement());
			stmt.setString(3, attend.getTimeIn().toString());
			stmt.setString(4, attend.getDate());
			
			//excute
			stmt.execute();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("SIGNING IN");
			alert.setContentText("SIGNED IN SUCCESSFULLY");
			alert.showAndWait();
		}
		catch(SQLException e) {
			
		}
	}
	public static void setEmployeeTimeOut(EmployeeAttendance attend) {
		LocalDate local = LocalDate.now();
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE attendance SET Time_out=? WHERE EmpID =? AND Date = '"+local.toString()+"' ");
			
			stmt.setString(1, attend.getTimeOut());
			stmt.setString(2, attend.getEmpID());
			
			//excute
			stmt.execute();
		
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("SIGNING OUT");
			alert.setContentText("SIGNED OUT SUCCESSFULLY");
			alert.showAndWait();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
