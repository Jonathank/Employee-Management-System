package database.employee;

public class BankDetails {

	//Bank Details
	private String empID;	//Employee ID number
	private String accountName; //Employee Account Name
	private String accountNum;	//Employee Account number
	
	//constructor
	public BankDetails(String id,String accountName,String accountNum) {
		this.empID =id;
		this.accountName = accountName;
		this.accountNum = accountNum;
	}
	
	public void setAccountName(String account) {
		this.accountName = account;
	}
	
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
		
	}
	
	public String getAccountName() {
		
		return this.accountName;
	}
	
	public String getAccountNum() {
		
		return accountNum;
	}
	public String getEmpID() {
		return this.empID;
		
	}

}//end
