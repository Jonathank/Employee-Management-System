package data.model.department;
/**
 * 
 * @author KASULE PAUL
 *
 */

public class Department {
	private String departID;	//Department ID
	private String depart;		//Department Name
	private String HOD;			//head of department
	
	public Department(String ID, String depart) {
		this.departID =ID;
		this.depart = depart;
	}
	public void setDepartID(String id) {
		this.departID = id;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public void setHOD(String hod) {
		this.HOD = hod;
	}
	public String getDepartID() {
		return this.departID;
	}
	public String getDepart() {
		return this.depart;
	}
	public String getHOD() {
		return this.HOD;
		
	}

}
