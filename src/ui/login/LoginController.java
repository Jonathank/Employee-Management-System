package ui.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.conn.DatabaseConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {
	@FXML
	private TextField txtusername;
	@FXML
	private PasswordField txtpass;
	@FXML
	private Button btnlogin,btnclose;
	@FXML
	private Label message;
	
	static PreparedStatement pstmt;
	static Connection conn = DatabaseConn.getConnection();
	Parent root;
	Scene scene;
	Stage stage;
	
	
	public void login(ActionEvent event) throws IOException  {
		
		if(txtusername.getText().isEmpty()) {
		showalert(AlertType.ERROR,(Stage)txtusername.getScene().getWindow(),"ERROR","PLEASE ENTER USERNAME");
		return;
		}
		if(txtpass.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtpass.getScene().getWindow(),"ERROR","PLEASE ENTER PASSWORD");
			return;
			}
		
		try {
			String sql = "SELECT * FROM users";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				if((rs.getString(1).equals(txtusername.getText())) && (rs.getString(2).equals(txtpass.getText()))){
					closeStage();
					loadpage();
				}
				else {
					message.setText("WRONG USERNAME OR PASSWORD");
				}
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	public void loadpage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/ui/home/home.fxml"));
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	public void closeStage() {
		stage = (Stage)btnlogin.getScene().getWindow();
		stage.close();
		
	}
	
	
	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	public void closeStage(ActionEvent event) {
		stage = (Stage)btnlogin.getScene().getWindow();
		stage.close();
		
	}
}
