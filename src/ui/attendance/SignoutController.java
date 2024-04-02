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

public class SignoutController implements Initializable{
	
	@FXML
private Button btnsave,btnsearch,btnclear,btnupdate,btntimein,btntimeout,btnsignin;
	@FXML
	private TextField txtid,txtdepart,txtsearch,txtdate,txtsignin;
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
	@FXML
	private TableColumn<EmployeeAttendance,String>tc_timeout;
	
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
			String SQL = "SELECT EMPID,DEPARTMENT,TIME_IN,TIME_OUT,DATE FROM attendance";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			{
			while(rs.next()) {
				try {
				EmployeeAttendance att = new EmployeeAttendance(rs.getString("empid"),rs.getString("department"));
				att.setTimeIn(rs.getString("time_in"));
				att.setTimeOut(rs.getString("time_out"));
				att.setDate(rs.getString("date"));
				list.add(att);
				}catch(Exception e) {
					
				}
				table.setItems(list);
				tc_id.setCellValueFactory(new PropertyValueFactory<>("empID"));
				tc_depart.setCellValueFactory(new PropertyValueFactory<>("departement"));
				tc_timein.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
				tc_timeout.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
				tc_date.setCellValueFactory(new PropertyValueFactory<>("date"));
			}
			}
	
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void signoutsearchbutton() {
		LocalDate local = LocalDate.now();
		try {
				String SQL = "SELECT EmpID,Department,Time_in,Date FROM attendance WHERE EmpID =? AND Date = '"+local.toString()+"'";
				PreparedStatement stmt = conn.prepareStatement(SQL);
				stmt.setString(1, txtsearch.getText());
				stmt.execute();
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					String id = rs.getString("Empid");
					String dep = rs.getString("department");
					String timein = rs.getString("time_in");
					String date = rs.getString("date");
			
					txtid.setText(id);
					txtdepart.setText(dep);
					txtdate.setText(date.toString());
					txtsignin.setText(timein.toString());
				
				try {
					String sql = "SELECT Photo FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, txtsearch.getText());
					pstmt.execute();
					ResultSet rs1 = pstmt.executeQuery();
					while(rs1.next()) {
						image = new Image(rs1.getBlob("photo").getBinaryStream());
						myimageview.setImage(image);
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public void signoutbutton(ActionEvent event) {
		 EmployeeAttendance attend = new  EmployeeAttendance(txtid.getText(),txtdepart.getText());
		 LocalTime time = LocalTime.now();
		attend.setTimeOut(time.toString());
		
		 
		EmployeeUtils.setEmployeeTimeOut(attend);
		table();
	}
	
	//loads sign in form
		public void loadattendance(ActionEvent event) throws IOException{
			closeStageM();
			root = FXMLLoader.load(getClass().getResource("/ui/attendance/attendace.fxml"));
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
