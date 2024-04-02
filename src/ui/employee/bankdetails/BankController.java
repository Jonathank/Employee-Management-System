package ui.employee.bankdetails;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import database.conn.DatabaseConn;
import database.employee.BankDetails;
import database.employee.EmployeeUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class BankController implements Initializable{
	@FXML
	private Button btnclear,btnsearch,btnsave;
	@FXML
	private TextField txtsearch,txtid,txtaccname,txtaccno; 
	
	@FXML
	private ImageView myimageview;
	@FXML
	private TableView<BankDetails>table;
	@FXML
	private TableColumn<BankDetails, String>tc_id;
	@FXML
	private TableColumn<BankDetails, String>tc_aname;
	@FXML
	private TableColumn<BankDetails, String>tc_acno;
	
	int myindex;
	Image image;
	
	static Connection conn = DatabaseConn.getConnection();
	
	
ObservableList<BankDetails>list = FXCollections.observableArrayList();
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table();
	}
	
	public void table() {
		
				try {
					table.getItems().clear();
					String SQL1 = "SELECT * FROM BANKDETAILS";
					Statement stmt1 = conn.createStatement();
					ResultSet rs1 = stmt1.executeQuery(SQL1);
					{
					while(rs1.next()) {
						try {
							
							BankDetails banks = new BankDetails(rs1.getString("empID"),rs1.getString("accountName").toString(),String.valueOf(rs1.getString("accountNo")));
							list.add(banks);
							
				}catch(SQLException e) {
					e.printStackTrace();
				}
						
				table.setItems(list);
				tc_id.setCellValueFactory(new PropertyValueFactory<>("empID"));
				tc_aname.setCellValueFactory(new PropertyValueFactory<>("accountName"));
				tc_acno.setCellValueFactory(new PropertyValueFactory<>("accountNum"));
				
			} 
			}
			
			table.setRowFactory(tv ->{
				TableRow<BankDetails>myrow = new TableRow<>();
				myrow.setOnMouseClicked(event ->{
				
					if(event.getClickCount() == 1 && (myrow !=null)) {
						myindex = table.getSelectionModel().getSelectedIndex();
						
						txtid.setText(table.getItems().get(myindex).getEmpID());
						txtaccname.setText(table.getItems().get(myindex).getAccountName());
						txtaccno.setText(table.getItems().get(myindex).getAccountNum());
						try {
							String SQL = "SELECT PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID=?";
							PreparedStatement stmtp = conn.prepareStatement(SQL);
							stmtp.setString(1, table.getItems().get(myindex).getEmpID());
							stmtp.execute();
							
							ResultSet rs = stmtp.executeQuery();
							while(rs.next()) {
								image = new Image(rs.getBlob("photo").getBinaryStream());
								myimageview.setImage(image);
							}
							}catch(SQLException e) {
							e.printStackTrace();
						}

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
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER EMPLOYEE ID");
		return;
	}
	if(txtaccname.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER ACCOUNT NAME");
		return;
	}
	if(txtaccno.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtid.getScene().getWindow(),"ERROR","PLEASE ENTER ACCOUNT NUMBER");
		return;
	}
	
	BankDetails bank = new BankDetails(txtid.getText(),txtaccname.getText(),txtaccno.getText());
	
	if(EmployeeUtils.IDExists(bank.getEmpID())) {
		 // if id exists it shows alert message of error, duplicate id
		showalert(AlertType.WARNING,((Stage)txtid.getScene().getWindow()),"CHECK","DUPLICATE ID");
	}
	
	EmployeeUtils.setEmployeeBankDetails(bank);
	
	table();
}
	
	public void clearbutton(ActionEvent event) {
		txtid.setText("");
		txtsearch.setText("");
		txtaccname.setText("");
		txtaccno.setText("");
		image = new Image("/photos/SIGNUP3.png");
		myimageview.setImage(image);
	}

	public void searchbutton(ActionEvent event) {
		try {
			String SQL = "SELECT EMPLOYEE_ID,PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,txtsearch.getText());
			pstmt.execute();
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				String id = rs.getString("employee_id");
				txtid.setText(id);
				Image image = new Image(rs.getBlob("photo").getBinaryStream());
				myimageview.setImage(image);
			
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
