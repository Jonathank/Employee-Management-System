package ui.employee.allowances;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import data.model.employee.attendence.bonus.SetAllowance;
import database.allowance.SetAllowanceDB;
import database.conn.DatabaseConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class AllowanceController implements Initializable{
	@FXML
	private TextField txtsearch,txtid,txtdep,txtallowance;
	@FXML
	private ComboBox<String>combocate;
	@FXML
	private ImageView myimageview;
	@FXML
	private Button btnsave,btnclear,btnsearch;
	@FXML
	private TableView<SetAllowance>table;
	@FXML
	private TableColumn<SetAllowance,String>TC_ID;
	@FXML
	private TableColumn<SetAllowance,String>TC_DEP;
	@FXML
	private TableColumn<SetAllowance,Double>TC_ALL;
	@FXML
	private TableColumn<SetAllowance,String>TC_CAT;
	
	static Connection conn =DatabaseConn.getConnection();
	
	ObservableList<SetAllowance>list = FXCollections.observableArrayList();
	int myindex;
	Image image;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		combocate.setItems(FXCollections.observableArrayList("DAILY","WEEKLY","MONTHLY"));
		table();
	}
	
	public void table() {
		try {
			table.getItems().clear();
			String SQL =  "SELECT EMPID,DEPARTMENT,ALLOWANCE,CATEGORY FROM BONUS";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			{
			while(rs.next()) {
				try {
				SetAllowance all = new SetAllowance(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4));
				list.add(all);
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
				table.setItems(list);
				TC_ID.setCellValueFactory(new PropertyValueFactory<>("empid"));
				TC_DEP.setCellValueFactory(new PropertyValueFactory<>("depart"));
				TC_ALL.setCellValueFactory(new PropertyValueFactory<>("allowance"));
				TC_CAT.setCellValueFactory(new PropertyValueFactory<>("category"));
				
			}
			}
			table.setRowFactory(tv ->{
				TableRow<SetAllowance>myrow = new TableRow<>();
				myrow.setOnMouseClicked(event ->{
					
				if(event.getClickCount()==1 && (myrow != null)) {
					
					myindex = table.getSelectionModel().getSelectedIndex();
					
					txtid.setText(table.getItems().get(myindex).getEmpid());
					txtdep.setText(table.getItems().get(myindex).getDepart());
					txtallowance.setText(String.valueOf( table.getItems().get(myindex).getAllowance()));
					combocate.setValue(String.valueOf(table.getItems().get(myindex).getCategory()));
				
					
					try {
						String SQL1 = "SELECT PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID =?";
						PreparedStatement pstmt = conn.prepareStatement(SQL1);
						pstmt.setString(1,table.getItems().get(myindex).getEmpid());
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
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void searchbutton(ActionEvent event) {
		try {
			String SQL = "SELECT EMPLOYEE_ID,DEPARTMENT,PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, txtsearch.getText());
			pstmt.execute();
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String empid = rs.getString("Employee_id");
				String dep = rs.getString("department");
				image = new Image(rs.getBlob("photo").getBinaryStream());
				
				myimageview.setImage(image);
				txtid.setText(empid);
				txtdep.setText(dep);
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
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
		if(combocate.getValue()==null) {
			showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE CHOOSE ALLOWANCE CATEGORY");
			return;
		}
		if(txtallowance.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE SET ALLOWANCE AMOUNT");
			return;
		}
		
		SetAllowance all = new SetAllowance(txtid.getText(),txtdep.getText(),Double.parseDouble(txtallowance.getText()),combocate.getValue().toString());
		SetAllowanceDB.setallowance(all);
		table();
	}
	
	public void clearbutton(ActionEvent event) {
		txtid.setText("");
		txtdep.setText("");
		txtallowance.setText("");
		txtsearch.setText("");
		combocate.setValue(null);
		image = new Image("/photos/SIGNUP3.png");
		myimageview.setImage(image);
	}

}
