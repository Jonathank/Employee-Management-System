package ui.employee.allowances;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ReportController implements Initializable{
	private String empid;
	private double allowance;
	private String paydate;
	private String fname ;
	private String lname;
	private String oname;
	private String department;
	
	  public ReportController(String emp,String fn,String ln,String on,String dep,double all,String pay) {
	        this.setEmpid(emp);
	        this.setAllowance(all);
	        this.setPaydate(pay);
	        this.setFname(fn);
	        this.setLname(ln);
	        this.setOname(on);
	        this.setDepartment(dep);
	    }
	    
	    @FXML
	    private AnchorPane root;
	    
	    @FXML
	    private Button btnPrint;
	    
	    @FXML
	    private Label tx_fn, tx_id, tx_all, tx_dep, tx_ln, tx_on, tx_pay;
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	parsevalues();
			
		}
	    
	    public void parsevalues() {
	        
	        tx_id.setText(empid);
	        tx_fn.setText(fname);
	        tx_ln.setText(lname);
	        tx_on.setText(oname);
	        tx_pay.setText(paydate);
	        tx_dep.setText(department);
	        tx_all.setText(allowance + " SHS.");
	    }
	    
	    @FXML
	    private void onButtonPrint() {
	        btnPrint.setVisible(false);
	        print(root);
	    }
	         
	    
	    private void print(Node node) {
	        PrinterJob job = PrinterJob.createPrinterJob();
	        if (job != null && job.showPrintDialog(node.getScene().getWindow())){
	            boolean success = job.printPage(node);
	            if (success) {
	                job.endJob();
	            }
	        }
	        btnPrint.setVisible(true);
	    }




		public String getEmpid() {
			return empid;
		}
		public void setEmpid(String empid) {
			this.empid = empid;
		}
		public double getAllowance() {
			return allowance;
		}
		public void setAllowance(double allowance) {
			this.allowance = allowance;
		}
		public String getPaydate() {
			return paydate;
		}
		public void setPaydate(String paydate) {
			this.paydate = paydate;
		}
		public String getFname() {
			return fname;
		}
		public void setFname(String fname) {
			this.fname = fname;
		}
		public String getLname() {
			return lname;
		}
		public void setLname(String lname) {
			this.lname = lname;
		}
		public String getOname() {
			return oname;
		}
		public void setOname(String oname) {
			this.oname = oname;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}

		
	    
	       
}
