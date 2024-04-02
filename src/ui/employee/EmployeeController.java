package ui.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import data.model.employee.Employee;
import database.conn.DatabaseConn;
import database.employee.EmployeeUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class EmployeeController implements Initializable {

	@FXML
	private Button btnsave,btnclear,btnupdate,btndelete,btnupload,btnsearch;
	@FXML
	private TextField txtid,txtsname,txtgname,txtoname,txtaddress,txtemail,txtjob,txtsalary,txtcontact,txtsearch;
	@FXML
	private Label labelid;
	@FXML
	private ComboBox<String> combogender;
	@FXML
	private ComboBox<String> combostatus;
	@FXML
	private ComboBox<String> combodepart;
	@FXML
	private ImageView myimageview;
	@FXML
	private DatePicker dob_datepicker,entry_datepicker;
	
	@FXML
	private TableView<Employee> employeetable;
	@FXML
	private TableColumn<Employee, String> TC_ID;
	@FXML
	private TableColumn<Employee, String> TC_FN;
	@FXML
	private TableColumn<Employee, String> TC_GN;
	@FXML
	private TableColumn<Employee, String> TC_ON;
	@FXML
	private TableColumn<Employee, String> TC_G;
	@FXML
	private TableColumn<Employee, String> TC_DEP;
	@FXML
	private TableColumn<Employee, String> TC_JOB;
	@FXML
	private TableColumn<Employee, String> TC_CON;
	@FXML
	private TableColumn<Employee, String> TC_STATUS;
	
	static Connection conn = DatabaseConn.getConnection();
	
	FileInputStream inputstream;
	static File myfile;
	Image image;
	FileChooser chooser = new FileChooser();
	int myindex;
	
	ObservableList<Employee> list = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		combogender.setItems(FXCollections.observableArrayList("MALE","FEMALE"));
		combostatus.setItems(FXCollections.observableArrayList("ACTIVE","TERMINATED"));
		table();
	}
	
	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	
	//collects all departs only in the DB to combobox departmrnt
	public void getdepartment() {
		try {
		String SQL = "SELECT DEPARTMENT_ID FROM DEPARTMENT";
		ObservableList<String>getlist =FXCollections.observableArrayList();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		 
		while(rs.next()) {
			String deplist = rs.getString(1);
			getlist.addAll(deplist);
			combodepart.setItems(getlist);
		}
		
		}catch(SQLException e) {
			
		}
	}
	
	
	
	@FXML
	public void table() {
		employeetable.getItems().clear();
		
		try {
			String SQL = "SELECT employee_ID,FirstName,GivenName,OtherName,Gender,Birth_date,Address,Contact, email,Department,jobTitle,Date_in,Salary,status FROM EMPLOYEE";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			{
			while(rs.next()) {
				try {
			
				Employee emp = new Employee( rs.getString("employee_ID"),rs.getString("FirstName"),rs.getString("GivenName"),rs.getString("OtherName"),rs.getString("Department"),rs.getDouble("Salary"));
				
				emp.setGender(rs.getString("Gender"));
				emp.setDOB(rs.getString("Birth_date"));
				emp.setAddress(rs.getString("Address"));
				emp.setContact(rs.getString("Contact"));
				emp.setEmail(rs.getString("email"));
				emp.setJobTitle(rs.getString("jobTitle"));
				emp.setStartDate(rs.getDate("Date_in"));
				emp.setStatus(rs.getString("status"));
				
				list.add(emp);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
				employeetable.setItems(list);
				TC_ID.setCellValueFactory(new PropertyValueFactory<>("empID"));
				TC_FN.setCellValueFactory(new PropertyValueFactory<>("surName"));
				TC_GN.setCellValueFactory(new PropertyValueFactory<>("givenName"));
				TC_ON.setCellValueFactory(new PropertyValueFactory<>("otherName"));
				TC_G.setCellValueFactory(new PropertyValueFactory<>("gender"));
				TC_DEP.setCellValueFactory(new PropertyValueFactory<>("department"));
				TC_JOB.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
				TC_CON.setCellValueFactory(new PropertyValueFactory<>("contact"));
				TC_STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));
				
				
			}
			}
			
			employeetable.setRowFactory(tv ->{
				
				TableRow<Employee> myrow = new TableRow<>();
				myrow.setOnMouseClicked(event -> {
					if(event.getClickCount() == 1 && (!myrow.isEmpty())) {
						
						myindex = employeetable.getSelectionModel().getSelectedIndex();
						
						 txtid.setText(employeetable.getItems().get(myindex).getEmpID());
						 txtsname.setText(employeetable.getItems().get(myindex).getSurName());
						 txtgname.setText(employeetable.getItems().get(myindex).getGivenName());
						 txtoname.setText(employeetable.getItems().get(myindex).getOtherName());
						 combogender.setValue(employeetable.getItems().get(myindex).getGender());
						 dob_datepicker.setValue(LocalDate.parse( employeetable.getItems().get(myindex).getDOB()));
						 txtaddress.setText(employeetable.getItems().get(myindex).getAddress());
						 txtcontact.setText(employeetable.getItems().get(myindex).getContact());
						 txtemail.setText(employeetable.getItems().get(myindex).getEmail());
						 combodepart.setValue(employeetable.getItems().get(myindex).getDepartment());
						 txtjob.setText(employeetable.getItems().get(myindex).getJobTitle());
						 
						 txtsalary.setText(String.valueOf(employeetable.getItems().get(myindex).getGrossSalary()));
						 combostatus.setValue(employeetable.getItems().get(myindex).getStatus());
					
							try {
								String SQL1 = "SELECT date_in,PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID =?";
								PreparedStatement pstmt = conn.prepareStatement(SQL1);
								pstmt.setString(1,employeetable.getItems().get(myindex).getEmpID());
								pstmt.execute();
								ResultSet rs1 = pstmt.executeQuery();
								while(rs1.next()) {
									image = new Image(rs1.getBlob("photo").getBinaryStream());
									myimageview.setImage(image);
									String date = rs1.getString("date_in");
									if(!(date == null)) {
										if(date.length()>0) {
									entry_datepicker.setValue(LocalDate.parse(date));
										}else {
											String n = "null";
											entry_datepicker.setValue(null);
											entry_datepicker.setValue(LocalDate.parse(String.valueOf( n)));
											showalert(AlertType.ERROR,(Stage)entry_datepicker.getScene().getWindow(),"WARNING","Employee with "+employeetable.getItems().get(myindex).getEmpID()+" has no Entry Date!");
											return;
										}
									}
								}
								
							}catch(SQLException e) {
								e.printStackTrace();}

						
						txtid.setVisible(true);
						labelid.setVisible(true);
					}
				});
				return myrow;
			});
			
	}catch(Exception e) {
		e.printStackTrace();
		}
	}
	
	
	//button uploadphoto codes
		public void uploadphoto(ActionEvent event) throws IOException {
			myfile = chooser.showOpenDialog((Stage)myimageview.getScene().getWindow());
			
			if(myfile != null) {
				image = new Image(myfile.toURI().toString());
				myimageview.setImage(image); 
			}
			else {
				showalert(AlertType.WARNING,(Stage)myimageview.getScene().getWindow(),"CANCLING PROCESS","IMAGE SELECTION FAILED");
			}
		}
	
	
	//button save codes
public void buttonsave(ActionEvent event) throws FileNotFoundException, SQLException {
	
	
	if(txtsname.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER SUR-NAME");
		return;
	}
	if(txtgname.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER  GIVEN NAME");
		return;
	}
	if(txtaddress.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER ADDRESS");
		return;
	}
	if(txtemail.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER EMAIL");
		return;
	}
	if(txtjob.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER JOBTITLE");
		return;
	}
	if(txtsalary.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER GROSS SALARY");
		return;
	}
	if(txtcontact.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER CONTACT");
		return;
	}
	if(combogender.getValue()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER GENDER");
		return;
	}
	if(combostatus.getValue()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER STATUS");
		return;
	}
	if(combodepart.getValue()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER DEPARTMENT");
		return;
	}
	
	if(dob_datepicker.getValue()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER BIRTH DATE");
		return;
	}
	if(entry_datepicker.getValue()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER ENTRY DATE");
		return;
	}
	if(myimageview.getImage()==null) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE LOAD EMPLOYEE PHOTO");
		return;
	}

	
	Employee emp = new Employee(txtsname.getText(),txtgname.getText(),txtoname.getText(),combodepart.getValue().toString(),Double.parseDouble(txtsalary.getText()));
	//emp.setEmpID(txtid.getText());
	emp.setGender(combogender.getValue().toString());
	emp.setDOB(dob_datepicker.getValue().toString());
	emp.setEmail(txtemail.getText());
	emp.setAddress(txtaddress.getText());
	emp.setContact(txtcontact.getText());
	emp.setJobTitle(txtjob.getText());
 //emp.setStartDate(entry);
	emp.setStatus(combostatus.getValue().toString());
	
	
	
	if(EmployeeUtils.isEmployeeIDExists(emp.getEmpID())) {
		 // if id exists it shows alert message of error, duplicate id
		 
		showalert(AlertType.WARNING,((Stage)txtid.getScene().getWindow()),"CHECK","DUPLICATE ID");
	}
	
	EmployeeUtils.addEmployee(emp, myfile);
	table();
	
}//end of button save


//delete button
public void deletebutton(ActionEvent event) throws IOException{
String empID = txtid.getText().toString();
EmployeeUtils.deleteEmployee(empID);
table();
}

//update button
public void updatebutton(ActionEvent event) throws FileNotFoundException {
	Employee emp = new Employee(txtsname.getText(),txtgname.getText(),txtoname.getText(),combodepart.getValue().toString(),Double.parseDouble(txtsalary.getText()));
	emp.setEmpID(txtid.getText());
	emp.setGender(combogender.getValue().toString());
	emp.setDOB(dob_datepicker.getValue().toString());
	emp.setEmail(txtemail.getText());
	emp.setAddress(txtaddress.getText());
	emp.setContact(txtcontact.getText());
	emp.setJobTitle(txtjob.getText());
	//emp.setStartDate(entry);
	emp.setStatus(combostatus.getValue().toString());
	
	EmployeeUtils.updateEmployee(emp,myfile);
	table();
	}


	


//clear button
public void clearbutton(ActionEvent event) {
	
	txtsearch.setText("");
	 txtid.setText("");
	 txtsname.setText("");
	 txtgname.setText("");
	 txtoname.setText("");
	 combogender.setValue(null);
	 dob_datepicker.setValue(null);
	 txtaddress.setText("");
	 txtcontact.setText("");
	 txtemail.setText("");
	 combodepart.setValue(null);
	 txtjob.setText("");
	 entry_datepicker.setValue(null);
	 txtsalary.setText("");
	 combostatus.setValue(null);
	 image = new Image("/photos/SIGNUP3.png");
	 myimageview.setImage(image);
	
}
//search button
public void search(ActionEvent event) {
	 try {
		 String SQL = "SELECT employee_id,firstname,GivenName,othername,gender,birth_date,address,contact,email,department,jobtitle,date_in,salary,status,photo FROM EMPLOYEE WHERE employee_id=?";
		 PreparedStatement pstmt = conn.prepareStatement(SQL);
		 
		 pstmt.setString(1, txtsearch.getText());
		 pstmt.execute();
		 ResultSet rs = pstmt.executeQuery();
		 while(rs.next()) {
			 Employee emp = new Employee( rs.getString("employee_ID"),rs.getString("FirstName"),rs.getString("GivenName"),rs.getString("OtherName"),rs.getString("Department"),rs.getDouble("Salary"));
				
				emp.setGender(rs.getString("Gender"));
				emp.setDOB(rs.getString("Birth_date"));
				emp.setAddress(rs.getString("Address"));
				emp.setContact(rs.getString("Contact"));
				emp.setEmail(rs.getString("email"));
				emp.setJobTitle(rs.getString("jobTitle"));
				emp.setStartDate(rs.getDate("Date_in"));
				emp.setStatus(rs.getString("status"));
				image = new Image(rs.getBlob("photo").getBinaryStream());
				
		     txtid.setText(emp.getEmpID().toString());
			 txtsname.setText(emp.getSurName().toString());
			 txtgname.setText(emp.getGivenName().toString());
			 txtoname.setText(emp.getOtherName().toString());
			 combogender.setValue(emp.getGender().toString());
			 dob_datepicker.setValue(LocalDate.parse(  emp.getDOB()));
			 txtaddress.setText(emp.getAddress().toString());
			 txtcontact.setText(emp.getContact());
			 txtemail.setText(emp.getEmail());
			 combodepart.setValue(emp.getDepartment().toString());
			 txtjob.setText(emp.getJobTitle());
			 entry_datepicker.setValue(LocalDate.parse( String.valueOf( emp.getStartDate())));
			 txtsalary.setText(String.valueOf(emp.getGrossSalary()));
			 combostatus.setValue(emp.getStatus().toString());
			 myimageview.setImage(image);
			 txtid.setVisible(true);
			 labelid.setVisible(true);
		
		 }
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
}

	
}
