package ui.employee.allowances;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import data.model.employee.attendence.bonus.PayAllowance;
import data.model.employee.attendence.bonus.SetAllowance;
import database.allowance.payAllowanceDB;
import database.conn.DatabaseConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class PayAllowanceController implements Initializable {
	@FXML
	private TextField txtid,txtsearch,txtdep,txtallowance,txtcate,txtreportid;
	@FXML
	private Button btnsave,btnclear,btnreport,btnsearch;
	@FXML
	private DatePicker datepicker;
	@FXML
	private ImageView myimageview;
	@FXML
	private TableView<PayAllowance>table;
	@FXML
	private TableColumn<PayAllowance,String>TC_ID;
	@FXML
	private TableColumn<PayAllowance,String>TC_DEP;
	@FXML
	private TableColumn<PayAllowance,Double>TC_ALL;
	@FXML
	private TableColumn<PayAllowance,String>TC_CATE;
	@FXML
	private TableColumn<PayAllowance,String>TC_PAY;
	
	static Connection conn = DatabaseConn.getConnection();
	ObservableList<PayAllowance>list = FXCollections.observableArrayList();
	int myindex;
	Image image;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table();
	}
	
	public void table() {
		table.getItems().clear();
		try {
			
			String SQL = "SELECT EMPID,ALLOWANCE,PAYDATE FROM PAYALLOWANCE";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			{
			while(rs.next()) {
				PayAllowance pay = new PayAllowance(rs.getString(1),rs.getDouble(2));
				pay.setDate(rs.getString(3).toString());
				list.add(pay);
				
			
			table.setItems(list);
			TC_ID.setCellValueFactory(new PropertyValueFactory<>("EmpID"));
			TC_ALL.setCellValueFactory(new PropertyValueFactory<>("amount"));
			TC_PAY.setCellValueFactory(new PropertyValueFactory<>("date"));
			}
			}
			table.setRowFactory(tv ->{
				TableRow<PayAllowance>myrow = new TableRow<>();
				myrow.setOnMouseClicked(event ->{
					
				if(event.getClickCount()==1 && (myrow != null)) {
					
					myindex = table.getSelectionModel().getSelectedIndex();
					
					txtid.setText(table.getItems().get(myindex).getEmpID());
					txtallowance.setText(String.valueOf( table.getItems().get(myindex).getAmount()));
					
				
					
					try {
						String SQL1 = "SELECT PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID =?";
						PreparedStatement pstmt = conn.prepareStatement(SQL1);
						pstmt.setString(1,table.getItems().get(myindex).getEmpID());
						pstmt.execute();
						ResultSet rs1 = pstmt.executeQuery();
						while(rs1.next()) {
							image = new Image(rs1.getBlob("photo").getBinaryStream());
							myimageview.setImage(image);
						}
						
					}catch(SQLException e) {
						e.printStackTrace();}

					try {
						String SQ = "SELECT Department, category FROM bonus WHERE EmpID =?";
						PreparedStatement stm = conn.prepareStatement(SQ);
						stm.setString(1, table.getItems().get(myindex).getEmpID());
						stm.execute();
						ResultSet r = stm.executeQuery();
						while(r.next()) {
							String de = r.getString("department");
							String cate = r.getString("category");
							txtcate.setText(cate.toString());
							txtdep.setText(de.toString());
							
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
					
				}
				});
				return myrow;
				
			});
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void searchbutton() {
		try {
			String SQL = "SELECT EMPID,DEPARTMENT,allowance,CATEGORY FROM BONUS WHERE EMPID = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, txtsearch.getText());
			pstmt.execute();
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
			SetAllowance all = new SetAllowance(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4));
			txtid.setText(all.getEmpid());
			txtdep.setText(all.getDepart());
			txtallowance.setText(String.valueOf(all.getAllowance()));
			txtcate.setText(all.getCategory());
	
			}	
			
			try {
				String SQL1 = "SELECT PHOTO FROM EMPLOYEE WHERE EMPLOYEE_ID =?";
				PreparedStatement stmt = conn.prepareStatement(SQL1);
				stmt.setString(1, txtsearch.getText());
				stmt.execute();
				ResultSet rs1 = stmt.executeQuery();
				while(rs1.next()) {
					image = new Image(rs1.getBlob("photo").getBinaryStream());
					myimageview.setImage(image);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void savebutton(ActionEvent event) throws IOException {
		
		PayAllowance pay = new PayAllowance(txtid.getText(),Double.parseDouble(txtallowance.getText()));
		LocalDate date = LocalDate.now();
		pay.setDate(date.toString());
		
		payAllowanceDB.payallowance(pay);
		
	}
	
	public void clearbutton(ActionEvent event) {
		txtsearch.setText("");
		txtid.setText("");
		txtdep.setText("");
		txtcate.setText("");
		txtallowance.setText("");
		 image = new Image("/photos/SIGNUP3.png");
		 myimageview.setImage(image);
		 
	}
	
	
	@FXML
    public void onButtonGReport(){
		
	try {
		String SQL = "SELECT firstname,givenname,othername,department FROM EMPLOYEE WHERE Employee_ID=?";
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, txtreportid.getText());
		pstmt.execute();
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			
		    String fn = rs.getString("firstname");
			String ln = rs.getString("givenname");
			String on = rs.getString("othername");
			String dep = rs.getString("department");
			
			try {
				LocalDate date = LocalDate.now();
				
				String sql = "SELECT empid,allowance,paydate FROM PAYALLOWANCE WHERE EMPID=? AND PAYDATE = '"+date.toString()+"'";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, txtreportid.getText());
				pst.execute();
				ResultSet rs1 = pst.executeQuery();
				while(rs1.next()) {
					String id = rs1.getString("empid");
					double all = rs1.getDouble("allowance");
					String payd = rs1.getString("paydate");
				
		
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ui/employee/allowances/Report.fxml"));
            
            ReportController controller = new ReportController(id,fn,ln,on,dep,all,payd);
            
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);
            Stage stage = new Stage();
            stage.setTitle("ALLOWANCE EMPLOYEE - REPORT");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            System.out.println("TRYING!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
  	}catch(SQLException e) {
  		e.printStackTrace();
  	}
	
	}
	
}
