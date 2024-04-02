package data.model.employee.attendence.bonus;
/**
 * 
 * @author KASULE PAUL
 *
 */

public class PayAllowance implements Allowances{
	private String EmpID;
	private String allowance;
	private double amount;
	private String department;
	private String date;
	
	public PayAllowance(String ID,double amount) {
		this.EmpID = ID;
		this.amount = amount;
	}

	@Override
	//handles employee allowance payment
	public void payAllowance(String allowance, double amount, String on) {
		// TODO Auto-generated method stub
		this.allowance =allowance;
		this.amount = amount;
		this.date = on;
		
	}
	//Returns employee ID
	public String getEmpID() {
		return this.EmpID;
	}
	
	//Returns allowance type
	public String getAllowance() {
		return this.allowance;
	}
	//Returns allowance amount
	public double getAmount() {
		return this.amount;
	}
	//Returns date of payment
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return this.date;
	}
	public String getDepartment() {
		return department;
		
	}
	
	
}
