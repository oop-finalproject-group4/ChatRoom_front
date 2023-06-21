package oopfinalproject.ChatRoomMainPage06181148;

import java.io.IOException;

//import oopfinalproject.ChatRoomMainPage06181148.API;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * 設定登入介面控制邏輯
 * 
 * 
 * */
public class logincontroller {
	
	@FXML
	private TextField useridTextField ;
	@FXML
	private PasswordField passwordPasswordField;

	
	private Stage stage;
	private Scene scene;
	private Parent root;
	

	private String password;
	private String userid;
	public void login_key(KeyEvent key) throws IOException{
		System.out.println("Key in");
		if (key.getCode().toString().equals("ENTER")) {
            String temp = "";
            temp =passwordPasswordField.getText();
            /**
             * 登入密碼輸入與偵測，並透過偵測鍵盤enter的形式達成。
             * */
            if(temp.equals("\n")) {
            	passwordPasswordField.clear();
            	return;
            }else {
            	temp =temp.substring(0, temp.length() - 1);
            	userid = useridTextField.getText();
        		System.out.println(userid);
        		password = passwordPasswordField.getText();
        		System.out.println(password);
        		
        		int resCode = API.login(userid, password);
        		
                if(resCode == 200) { 
                	//登入成功跳轉至主畫面
        			root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        			stage = (Stage)((Node)key.getSource()).getScene().getWindow();
        			scene = new Scene(root);
        			stage.setScene(scene);
        			stage.show();
                }
                else {
                	//登入失敗重新登入
                	Stage popupStage = new Stage();
        	        popupStage.initModality(Modality.APPLICATION_MODAL);
        	        popupStage.setTitle("Login Failed");
        	        
        	        Label messageLabel = new Label("登入失敗\n請重新確認帳號密碼");
        	        messageLabel.setWrapText(true);
        	        messageLabel.setAlignment(Pos.CENTER);
        	        messageLabel.setTextAlignment(TextAlignment.CENTER);

        	        Button closeButton = new Button("重新登入");
        	        closeButton.setOnAction(e -> popupStage.close());

        	        VBox popupRoot = new VBox(messageLabel, closeButton);
        	        popupRoot.setAlignment(Pos.CENTER);
        	        popupRoot.setSpacing(10);
        	        popupRoot.setPadding(new Insets(20));

        	        Scene popupScene = new Scene(popupRoot, 250, 150);
        	        popupStage.setScene(popupScene);
        	        popupStage.showAndWait();

                	System.out.println("Login failed");
                	passwordPasswordField.clear();
                	passwordPasswordField.requestFocus();
                	
                }

            }
        }
	}
	
	public void login(ActionEvent event) throws IOException {
		/*
		 * 透過按鈕形式登入
		 * 
		 * */
		userid = useridTextField.getText();
		System.out.println(userid);
		password = passwordPasswordField.getText();
		System.out.println(password);
		
		int resCode = API.login(userid, password);
		
        if(resCode == 200) { 
			root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
        }
        else {
        	Stage popupStage = new Stage();
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setTitle("Login Failed");
	        
	        Label messageLabel = new Label("登入失敗\n請重新確認帳號密碼");
	        messageLabel.setWrapText(true);
	        messageLabel.setAlignment(Pos.CENTER);
	        messageLabel.setTextAlignment(TextAlignment.CENTER);

	        Button closeButton = new Button("重新登入");
	        closeButton.setOnAction(e -> popupStage.close());

	        VBox popupRoot = new VBox(messageLabel, closeButton);
	        popupRoot.setAlignment(Pos.CENTER);
	        popupRoot.setSpacing(10);
	        popupRoot.setPadding(new Insets(20));

	        Scene popupScene = new Scene(popupRoot, 250, 150);
	        popupStage.setScene(popupScene);
	        popupStage.showAndWait();

        	System.out.println("Login failed");
        	passwordPasswordField.clear();
        	passwordPasswordField.requestFocus();
        }

	}
	/*
	public void login(ActionEvent event) throws IOException {
		
		userid = useridTextField.getText();
		System.out.println(userid);
		password = passwordPasswordField.getText();
		System.out.println(password);
		
		int resCode = API.login(userid, password);
		
        if(resCode == 200) { 
			root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
        }
        else {
        	Stage popupStage = new Stage();
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setTitle("Login Failed");
	        
	        Label messageLabel = new Label("登入失敗\n請重新確認帳號密碼");
	        messageLabel.setWrapText(true);
	        messageLabel.setAlignment(Pos.CENTER);
	        messageLabel.setTextAlignment(TextAlignment.CENTER);

	        Button closeButton = new Button("重新登入");
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

	}
	*/
    public void switchToSignup(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("registration.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
	
	
}
