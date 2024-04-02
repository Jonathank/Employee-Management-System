package data.model.payRoll;



/**
 * 
 * @author KASULE PAUL
 *
 */

public class PayRoll implements Reducations{
	private String EmpId;		//Employee ID for the employee on the payroll
	private String accountNo;
	private double grossSalary;	//Employee gross Salary
	private double NetSalary;	//Employee basic salary
	private double nssf;		//NSSF Reducation
	private double payAsYouEarn;
	private String paydate;
	private String paymonth;
	
	
	/**
	 * Constructor
	 * @param empId
	 * @param accountNo
	 * @param gSalary
	 */
	public PayRoll(String empId,String accountNo, double gSalary) {
		this.EmpId = empId;
		this.accountNo = accountNo;
		this.grossSalary = gSalary;
	}
	
	//Sets the employee gross Salary
	public void setGrossSalary(double salary) {
		this.grossSalary = salary;
	}
	//Sets the Employee basic salary
	public void setNetSalary(double salary) {
		this.NetSalary = salary;
	}
	public void setNssf(double nssf) {
		this.nssf = nssf;
	}
	
	public double getNssf() {
		return nssf;
	}
	public void setPayAsYouEarn(double paye) {
		this.payAsYouEarn = paye;
	}
	public double getPayAsYouEarn() {
		return payAsYouEarn;
	}
	
	//Returns Employee ID
	public void setEmpId(String empid) {
		this.EmpId = empid;
	}
	public String getEmpId() {
		return this.EmpId;	
	}
	//Returns Department
	//public String getDepartment() {
		//return this.departID;
	//}
	//Return gross salary
	public double getGrossSalary() {
		return this.grossSalary;
	}
	//Return basic salary
	public double getNetSalary() {
		//removes nssf and pay as you earn from Gross Salary
		this.NetSalary = this.grossSalary- this.payAsYouEarn- this.nssf;
		return this.NetSalary;
		
	}
	//Calculates and returns the NSSF
	@Override
	public double calNSSF() {
		// TODO Auto-generated method stub
		this.nssf = this.grossSalary*0.05;
		 
		return this.nssf;
	}
//Calculates and returns the pay as you earn tax
	@Override
	public double calPayE() {
		// TODO Auto-generated method stub
		
		if(this.grossSalary <= 235000) {
			this.payAsYouEarn = 0;
		}
		else if((this.grossSalary > 235000)&& (this.grossSalary< 335000)) {
			double excess = this.grossSalary - 235000;
			double tax = (excess *0.01);

			this.payAsYouEarn =tax;
			
		}
		else if((this.grossSalary > 335000) && (this.grossSalary <410000)) {
			double excess = this.grossSalary - 335000;
			double tax = (excess * 0.01)+ 10000;
			this.payAsYouEarn = tax;
		}
		else if((this.grossSalary > 410000) &&(this.grossSalary <10000000)) {
			double excess = this.grossSalary - 410000;
			double tax = (excess * 0.3)+25000;
			this.payAsYouEarn = tax;
			
		}
		return this.payAsYouEarn;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getPaymonth() {
		return paymonth;
	}

	public void setPaymonth(String paymonth) {
		this.paymonth = paymonth;
	}

}//end 
