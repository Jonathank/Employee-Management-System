package ui.attendance;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import data.model.employee.attendence.EmployeeAttendance;
import database.conn.DatabaseConn;
import database.employee.EmployeeUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AttendanceController implements Initializable{
	@FXML
private Button btnsave,btnsearch,btnclear,btnupdate,btntimein,btntimeout,btnsignout;
	@FXML
	private TextField txtid,txtdepart,txtsearch,txtdate;
	@FXML
	private DatePicker datepicker;
	@FXML
	private ImageView myimageview;
	@FXML
	private TableView<EmployeeAttendance>table;
	@FXML
	private TableColumn<EmployeeAttendance,String>tc_id;
	@FXML
	private TableColumn<EmployeeAttendance,String>tc_depart;
	@FXML
	private TableColumn<EmployeeAttendance,String>tc_date;
	@FXML
	private TableColumn<EmployeeAttendance,String>tc_timein;
	
	
	static Connection conn = DatabaseConn.getConnection();
	ObservableList<EmployeeAttendance>list = FXCollections.observableArrayList();
	int myindex;
	Image image;
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table();
		
	}
	
	public void table() {
		table.getItems().clear();
		try {
			String SQL = "SELECT EMPID,DEPARTMENT,TIME_IN,DATE FROM attendance";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			{
			while(rs.next()) {
				try {
				EmployeeAttendance att = new EmployeeAttendance(rs.getString("empid"),rs.getString("department"));
				att.setTimeIn(rs.getString("time_in").toString());
				att.setDate(rs.getString("date"));
				list.add(att);
				
				}catch(Exception e) {
					
				}
				table.setItems(list);
				tc_id.setCellValueFactory(new PropertyValueFactory<>("empID"));
				tc_depart.setCellValueFactory(new PropertyValueFactory<>("departement"));
				tc_timein.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
				tc_date.setCellValueFactory(new PropertyValueFactory<>("date"));
			}
			}
	
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void searchbutton() {
		try {
				String SQL = "SELECT EMPLOYEE_ID,DEPARTMENT,PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID =?";
				PreparedStatement stmt = conn.prepareStatement(SQL);
				stmt.setString(1, txtsearch.getText());
				stmt.execute();
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					String id = rs.getString("Employee_id");
					String dep = rs.getString("department");
					
					txtid.setText(id);
					txtdepart.setText(dep);
					image = new Image(rs.getBlob("photo").getBinaryStream());
					myimageview.setImage(image);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public void signin(ActionEvent event) {
		EmployeeAttendance attend = new EmployeeAttendance(txtid.getText(),txtdepart.getText());
		
		LocalDate local = LocalDate.now();
		attend.setDate(local.toString());
		LocalTime time = LocalTime.now();
		attend.setTimeIn(time.toString());
		
		 EmployeeUtils.setEmployeeTimeIn(attend); 
	}

	
	//loads signout form
	public void loadsignout(ActionEvent event) throws IOException{
		closeStageM();
		root = FXMLLoader.load(getClass().getResource("/ui/attendance/signout.fxml"));
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void closeStageM() {
		stage = (Stage)txtid.getScene().getWindow();
		stage.close();
		
	}

}
