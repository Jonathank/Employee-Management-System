package data.model.employee;

import java.util.Date;
import database.employee.EmployeeUtils;

/**
 * 
 * @author JONATHAN 
 *
 */
public class Employee {
	//Employee fields
	private static int auto_id;	//Automatically generates based on the last id in the database
	private String empID;		//Employee identification Number
	private String surName;		//Employee surName
	private String givenName;	//Given Name
	private String otherName;	//last name
	private String gender;		//Sex
	private String DOB;	//employee date of birth
	private String address;
	private String contact;
	private String email;
	private String department;
	private Date startDate;
	private String jobTitle; 
	private double grossSalary;
	private String status;
	//private double weeklyAllowance;
	
	/**
	 * 
	 * @param ID
	 * @param sName
	 * @param gName
	 * @param oName
	 * @param depart
	 * @param salary
	 */
	public Employee(String sName,String gName,String oName,String depart,double salary) {
		
		this.surName = sName;
		this.givenName = gName;
		this.otherName = oName;
		this.department = depart;
		this.grossSalary = salary;
		auto_id = EmployeeUtils.getLastEmployeeID();//Retrive the last auto id number from database
		EmployeeUtils.saveId(auto_id);//Save the id number in the database
		

		if(auto_id<=999) {
			String idSt = "SE000"+auto_id;
			this.empID = idSt;	
		}
		else if(auto_id >= 1000) {
			String idSt = "SEOO"+auto_id;
			this.empID = idSt;
		}
	}
	
	//default constructor
public Employee(String empID,String sName,String gName,String oName,String depart,double salary) {
		this.empID = empID;
		this.surName = sName;
		this.givenName = gName;
		this.otherName = oName;
		this.department = depart;
		this.grossSalary = salary;
}
	
	
	public void setEmpID(String id) {
		this.empID = id;
		
	}
	public void setSurName(String sName) {
		this.surName =sName;
	}
	public void setGivenName(String gName) {
		this.givenName = gName;
	}
	public void setOtherName(String oName) {
		this.otherName = oName;
	}
	public void setGender(String sex) {
		this.gender = sex;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setContact(String tel) {
		this.contact = tel;
	}
	public void setEmail(String mail) {
		this.email = mail;
	}
	public void setDepartment(String depart) {
		this.department = depart;
	}
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	public void setDOB(String DOB) {
		this.DOB = DOB;
	}
	public void setJobTitle(String jobt) {
		this.jobTitle = jobt;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getEmpID() {
		return this.empID;
	}
	public String getSurName() {
		return this.surName;
	}
	public String getGivenName() {
		return this.givenName;
	}
	public String getOtherName() {
		if(this.otherName == null) {
			return "";
		}
		return this.otherName;
		
	}
	public String getDOB() {
		return this.DOB;
	}
	public String getGender() {
		return this.gender;
	}
	public String getAddress() {
		return this.address;
	}
	public String getContact() {
		return this.contact;
	}
	public String getDepartment() {
		return this.department;
	}
	public String getEmail() {
		return this.email;		
	}
	public Date getStartDate() {
		return this.startDate;
	}
	public String getJobTitle() {
		return this.jobTitle;	
	}
	public double getGrossSalary() {
		return this.grossSalary;
	}
	public String getStatus() {
		return this.status;
	}
}//end 
