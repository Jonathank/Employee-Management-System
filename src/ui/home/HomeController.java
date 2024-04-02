package ui.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController implements Initializable {


	@FXML
	private Button btnlogout;
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	//loads attendance form
			public void loadattendance(ActionEvent event) throws IOException{
				root = FXMLLoader.load(getClass().getResource("/ui/attendance/attendace.fxml"));
				scene = new Scene(root);
				stage = new Stage();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			}
	
	//loads payallowanceform
		public void loadpayallowance(ActionEvent event) throws IOException{
			root = FXMLLoader.load(getClass().getResource("/ui/employee/allowances/payAllowance.fxml"));
			scene = new Scene(root);
			stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}
	
	//loads setallowance form
	public void loadsetallowancce(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("/ui/employee/allowances/allawance.fxml"));
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	//loads payroll form
			public void loadpayroll(ActionEvent event) throws IOException{
				root = FXMLLoader.load(getClass().getResource("/ui/payroll/payroll.fxml"));
				scene = new Scene(root);
				stage = new Stage();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			}
	//loads bankdetails form
		public void loadbankdetails(ActionEvent event) throws IOException{
			root = FXMLLoader.load(getClass().getResource("/ui/employee/bankdetails/bank.fxml"));
			scene = new Scene(root);
			stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}
	//loads employee form
	public void loademp(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("/ui/employee/employee.fxml"));
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	//loads department form
	public void loadDepartment(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("/ui/department/department.fxml"));
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	//logs out of the home page
	public void logout(ActionEvent event) throws IOException{
		closeStageM();
		root = FXMLLoader.load(getClass().getResource("/ui/login/login.fxml"));
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void closeStageM() {
		stage = (Stage)btnlogout.getScene().getWindow();
		stage.close();
		
	}
	
}
