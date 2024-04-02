package data.model.employee.attendence.bonus;

public class SetAllowance {
	private String empid;
	private String depart;
	private double allowance;
	private String category;
	
	public SetAllowance(String empid,String dep,double all,String cat) {
		this.empid = empid;
		this.depart = dep;
		this.allowance = all;
		this.category = cat;
	}
	
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public double getAllowance() {
		return allowance;
	}
	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
