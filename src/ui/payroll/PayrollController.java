package ui.payroll;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import data.model.payRoll.PayRoll;
import database.conn.DatabaseConn;
import database.payroll.payrollDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PayrollController implements Initializable {
@FXML
private TextField txtid,txtaccno,txtgross,txtnssf,txtpaye,txtbasic,txtsearch;
@FXML
private Button btnsave,btnsearch,btnclear,btnreport;
@FXML
private DatePicker datepicker;
@FXML
private ComboBox<String>combomonth;
@FXML
private ImageView myimageview;
@FXML
private TableView<PayRoll>table;
@FXML
private TableColumn<PayRoll, String> TC_ID;
@FXML
private TableColumn<PayRoll, String>TC_ACCNO;
@FXML
private TableColumn<PayRoll, Double>TC_GROSS;
@FXML
private TableColumn<PayRoll, Double>TC_NSSF;
@FXML
private TableColumn<PayRoll, Double>TC_PAYE;
@FXML
private TableColumn<PayRoll, Double>TC_BASIC;
@FXML
private TableColumn<PayRoll, String>TC_PAYM;
@FXML
private TableColumn<PayRoll, String>TC_PAYD;

int myindex;
Image image;
static Connection conn = DatabaseConn.getConnection();

ObservableList<PayRoll>list = FXCollections.observableArrayList();


@Override
public void initialize(URL location, ResourceBundle resources) {
combomonth.setItems(FXCollections.observableArrayList("JAN","FEB","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"));
table();
}
@FXML
public void table() {
	try {
		table.getItems().clear();
		
		String SQL = "SELECT EmpID,accountno,grosssalary,nssf_pay,payasyou_earn,basicsalary,paymonth,paydate FROM PAYROLL";
		
		Statement  stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		{
		while(rs.next()) {
			try {
			PayRoll pay = new PayRoll(rs.getString("EmpID"),rs.getString("accountno"),rs.getDouble("Grosssalary"));
			pay.setNssf(rs.getDouble("nssf_pay"));
			pay.setPayAsYouEarn(rs.getDouble("PayAsyou_earn"));
			pay.setNetSalary(rs.getDouble("basicsalary"));
			pay.setPaymonth(rs.getString("paymonth"));
			pay.setPaydate(rs.getString("paydate"));
			list.addAll(pay);
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			table.setItems(list);
			TC_ID.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
			TC_ACCNO.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
			TC_GROSS.setCellValueFactory(new PropertyValueFactory<>("grossSalary"));
			TC_NSSF.setCellValueFactory(new PropertyValueFactory<>("nssf"));
			TC_PAYE.setCellValueFactory(new PropertyValueFactory<>("payAsYouEarn"));
			TC_BASIC.setCellValueFactory(new PropertyValueFactory<>("NetSalary"));
			TC_PAYM.setCellValueFactory(new PropertyValueFactory<>("paymonth"));
			TC_PAYD.setCellValueFactory(new PropertyValueFactory<>("paydate"));
			
		}
		}
		
		table.setRowFactory(tv ->{
			TableRow<PayRoll>myrow = new TableRow<>();
			myrow.setOnMouseClicked(event ->{
				
			if(event.getClickCount()==1 && (myrow != null)) {
				
				myindex = table.getSelectionModel().getSelectedIndex();
				
				txtid.setText(table.getItems().get(myindex).getEmpId().toString());
				txtaccno.setText(table.getItems().get(myindex).getAccountNo());
				txtgross.setText(String.valueOf( table.getItems().get(myindex).getGrossSalary()));
				txtnssf.setText(String.valueOf( table.getItems().get(myindex).getNssf()));
				txtpaye.setText(String.valueOf( table.getItems().get(myindex).getPayAsYouEarn()));
				txtbasic.setText(String.valueOf(table.getItems().get(myindex).getNetSalary()));
				combomonth.setValue(String.valueOf(table.getItems().get(myindex).getPaymonth()));
				datepicker.setValue(LocalDate.parse(table.getItems().get(myindex).getPaydate()));
				
				try {
					String SQL1 = "SELECT PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID =?";
					PreparedStatement pstmt = conn.prepareStatement(SQL1);
					pstmt.setString(1,table.getItems().get(myindex).getEmpId());
					pstmt.execute();
					ResultSet rs1 = pstmt.executeQuery();
					while(rs1.next()) {
						image = new Image(rs1.getBlob("photo").getBinaryStream());
						myimageview.setImage(image);
					}
					
				}catch(SQLException e) {
					e.printStackTrace();}
				
			}
			});
			return myrow;
			
		});
		
		
		
	}catch(Exception e) {
		e.printStackTrace();
	}
}
@FXML
public void searchbutton(ActionEvent event) {
	try {
		String SQL = "SELECT EMPLOYEE_ID,SALARY,PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(SQL);
		stmt.setString(1, txtsearch.getText());
		stmt.execute();
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			String empid = rs.getString("employee_id");
			double salary =rs.getDouble("salary");
			
			txtid.setText(empid);
			txtgross.setText(String.valueOf( salary));
			image = new Image(rs.getBlob("photo").getBinaryStream());
			myimageview.setImage(image);
			
		}
		
		try {
			String SQL1 = "SELECT ACCOUNTNO FROM BANKDETAILS WHERE EMPID =? ";
			PreparedStatement pstmt = conn.prepareStatement(SQL1);
			pstmt.setString(1, txtsearch.getText());
			pstmt.execute();
			ResultSet rs1 = pstmt.executeQuery();
			while(rs1.next()) {
				txtaccno.setText(rs1.getString("accountNo").toString());
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	calculate();
}


public void calculate() {
	double gross = Double.parseDouble(txtgross.getText());
	if(Double.valueOf(gross)!=null){
		double nssf = gross*0.05;
		txtnssf.setText(String.valueOf(nssf));
		
		double paye = 0;
		if(gross <= 235000) {
			paye = 0;
			txtpaye.setText(String.valueOf(paye));
		}else if((gross >235000) && (gross <= 335000)) {
			double excess = gross - 235000;
			paye = (excess * 0.01);
			txtpaye.setText(String.valueOf(paye));
		}else if((gross > 335000) && (gross <= 410000)) {
			double excess = gross -335000;
			paye = (excess * 0.01)+ 10000;
			txtpaye.setText(String.valueOf(paye));
		}else if((gross > 410000) && (gross <= 10000000)) {
			double excess = gross - 410000;
			paye = (excess * 0.3)+ 25000;
			txtpaye.setText(String.valueOf(paye));
		}
		
		double netsalary = gross - paye -nssf;
		txtbasic.setText(String.valueOf(netsalary));
		
	}
}

private static void showalert(AlertType alerttype,Window owner,String title,String message) {
	Alert alert = new Alert(alerttype);
	alert.setTitle(title);
	alert.setHeaderText(null);
	alert.setContentText(message);
	alert.initOwner(owner);
	alert.show();
}


public void savebutton(ActionEvent event) throws Exception {
	if(combomonth.getValue()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER MONTH PAID FOR");
		return;
	}
	if(datepicker.getValue()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER DATE ");
		return;
	}
	
	PayRoll payroll = new PayRoll(txtid.getText(),txtaccno.getText(),Double.parseDouble(txtgross.getText()));
	payroll.setNetSalary(Double.parseDouble(txtbasic.getText()));
	payroll.setNssf(Double.parseDouble(txtnssf.getText()));
	payroll.setPayAsYouEarn(Double.parseDouble(txtpaye.getText()));
	payroll.setPaydate(datepicker.getValue().toString());
	payroll.setPaymonth(combomonth.getValue().toString());
	
	if(payrollDB.isEmpIDExists(payroll.getEmpId())) {
		showalert(AlertType.WARNING,((Stage)txtid.getScene().getWindow()),"CHECK","DUPLICATE ID");
	}
	payrollDB.makepayment(payroll);
	table();
}

public void clearbutton(ActionEvent event) {
	txtid.setText("");
	txtaccno.setText("");
	txtgross.setText("");
	txtbasic.setText("");
	txtnssf.setText("");
	txtpaye.setText("");
	datepicker.setValue(null);
	combomonth.setValue(null);
	txtsearch.setText("");
	Image image = new Image("/photos/SIGNUP3.png");
	myimageview.setImage(image);
}

}
