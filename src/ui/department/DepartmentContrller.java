package ui.department;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import data.model.department.Department;
import database.conn.DatabaseConn;
import database.department.DepartmentUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DepartmentContrller implements Initializable{
	@FXML
	private Button btnsave,btnclear,btnupdate,btndelete,btnsearch;

	@FXML
	private TextField txtid,txtdname,txthod,txtsearch;
	@FXML
	private TableView<Department>table;
	@FXML
	private TableColumn<Department,String> tc_id;
	@FXML
	private TableColumn<Department,String> tc_dname;
	@FXML
	private TableColumn<Department,String> tc_hod;
	


	
	static Connection conn = DatabaseConn.getConnection();
	ObservableList<Department>list = FXCollections.observableArrayList();
	int myindex;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table();
	}
	
	public void table() {
		try {
			table.getItems().clear();
			String SQL = "SELECT * FROM DEPARTMENT";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			{
			while(rs.next()) {
				try {
					Department dep = new Department(rs.getString(1),rs.getString(2));
					dep.setHOD(rs.getString(3));
					list.add(dep);
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
				table.setItems(list);
				tc_id.setCellValueFactory(new PropertyValueFactory<>("departID"));
				tc_dname.setCellValueFactory(new PropertyValueFactory<>("depart"));
				tc_hod.setCellValueFactory(new PropertyValueFactory<>("HOD"));
				
			}
			}
			
			table.setRowFactory(tv ->{
				TableRow<Department> myrow = new TableRow<>();
				myrow.setOnMouseClicked(event -> {
					if(event.getClickCount() == 1 && myrow != null) {
						myindex = table.getSelectionModel().getSelectedIndex();
						
						txtid.setText(table.getItems().get(myindex).getDepartID());
						txtdname.setText(table.getItems().get(myindex).getDepart());
						txthod.setText(table.getItems().get(myindex).getHOD());
					}
					
				});
				return myrow;
			});
			
		}catch(Exception e) {
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
	
	public void savebutton(ActionEvent event) {
		if(txtid.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER DEPARTMENT ID");
		return;
		}
		if(txtdname.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER DEPARTMENT NAME");
		return;
		}
		
		Department dep = new Department(txtid.getText(),txtdname.getText());
		dep.setHOD(txthod.getText());
		
		if(DepartmentUtil.isDepartmentIDExists(txtid.getText())) {
			/**
			 * if id exists it shows alert message of error, duplicate id
			 */
			showalert(AlertType.WARNING,((Stage)txtid.getScene().getWindow()),"CHECK","DUPLICATE ID");
		}
		
		DepartmentUtil.addDepartment(dep);
		table();
		
	}
	
	public void deletebutton(ActionEvent event) {
		//String id = txtid.getText();
		//DatabaseOps.delete(id);
		table();
	}
	
	public void updatebutton(ActionEvent event) {
		//Department dep = new Department(txtid.getText(),txtdname.getText(),txthod.getText());
		//DatabaseOps.update(dep);
		table();
	}
	
	public void clearbutton(ActionEvent event) {
		txtid.setText("");
		txtdname.setText("");
		txthod.setText("");
		txtsearch.setText("");
	}
	
	public void searcchbutton(ActionEvent event) {
		try {
			String SQL = "SELECT * FROM DEPARTMENT WHERE DEPARTMENT_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, txtsearch.getText());
			pstmt.execute();
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Department dep = new Department(rs.getString(1),rs.getString(2));
				dep.setHOD(rs.getString(3));
				txtid.setText(dep.getDepartID());
				txtdname.setText(dep.getDepart());
				txthod.setText(dep.getHOD());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

}
