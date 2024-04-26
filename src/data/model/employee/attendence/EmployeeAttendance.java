package data.model.employee.attendence;


import java.time.LocalTime;
/**
 * 
 * @author JONATHAN
 *
 */
public class EmployeeAttendance implements Attendence {
	private String empID;	//Employee identification Number
	private String timeIn; 	//time when the employee signs in
	private String timeOut;//Time when the employee signs out
	private String departement; //depart
	private String date;
	
	
	
	public EmployeeAttendance(String empID,String depart) {
		this.empID = empID;
		this.departement = depart;
	}
	


	@Override
	public String getTimeInn() {
		// TODO Auto-generated method stub
		//Get the system date and Time
		LocalTime now = LocalTime.now();
		String date = now.toString();
		this.timeIn =date;
		
		return this.timeIn;
	}


	@Override
	public String getTimeOutt() {
		// TODO Auto-generated method stub
		//Get the system date and Time
		LocalTime now = LocalTime.now();
		String date = now.toString();
		this.timeOut = date;
		return this.timeOut;
	}
	
	
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public String getTimeIn() {
		return timeIn;
	}
	
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	
}
