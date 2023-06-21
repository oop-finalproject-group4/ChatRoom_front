package oopfinalproject.ChatRoomMainPage06181148;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class registrationcontroller {
	/*
	 * 註冊設定
	 * */
	
	@FXML
	private TextField emailTextField ;
	@FXML
	private  TextField passwordTextField;
	@FXML
	private TextField useridTextField;
	@FXML
	private TextField nameTextField;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private String email;
	private String name;
	private String password;
	private String userid;
	
	public void registration(ActionEvent event) throws IOException {
		name = nameTextField.getText();
		userid = useridTextField.getText();
		password = passwordTextField.getText();
		email = emailTextField.getText();
		System.out.println(name);
		System.out.println(password);
		System.out.println(email);
		/*
		 * 透過API去抓取註冊資料等等的資訊，並進行確認註冊，還有確認格式
		 * */
		int resCode = API.signup(userid, password, name, email);
		System.out.println(resCode);
		
		if(resCode == 200) {
			
			root = FXMLLoader.load(getClass().getResource("login.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else if (resCode == 409) {
			Stage popupStage = new Stage();
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setTitle("Registration Failed");

	        
	        Label messageLabel = new Label("此帳號已存在\n可直接登入");
	        messageLabel.setWrapText(true);
	        messageLabel.setAlignment(Pos.CENTER);
	        messageLabel.setTextAlignment(TextAlignment.CENTER);

	        Button closeButton = new Button("確認");
	        closeButton.setOnAction(e -> popupStage.close());

	        VBox popupRoot = new VBox(messageLabel, closeButton);
	        popupRoot.setAlignment(Pos.CENTER);
	        popupRoot.setSpacing(10);
	        popupRoot.setPadding(new Insets(20));

	        Scene popupScene = new Scene(popupRoot, 250, 150);
	        popupStage.setScene(popupScene);
	        popupStage.showAndWait();
        	System.out.println("Login failed");
		}
		else {
			Stage popupStage = new Stage();
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setTitle("Registration Failed");

	        
	        Label messageLabel = new Label("註冊失敗\n請確認資料格式皆符合要求");
	        messageLabel.setWrapText(true);
	        messageLabel.setAlignment(Pos.CENTER);
	        messageLabel.setTextAlignment(TextAlignment.CENTER);

	        Button closeButton = new Button("確認");
	        closeButton.setOnAction(e -> popupStage.close());

	        VBox popupRoot = new VBox(messageLabel, closeButton);
	        popupRoot.setAlignment(Pos.CENTER);
	        popupRoot.setSpacing(10);
	        popupRoot.setPadding(new Insets(20));

	        Scene popupScene = new Scene(popupRoot, 250, 150);
	        popupStage.setScene(popupScene);
	        popupStage.showAndWait();
		}
			
	}
}
