package oopfinalproject.ChatRoomMainPage06181148;
	
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;


public class Main extends Application {
	//初始進入畫面與介面
	@Override
	public void start(Stage primaryStage) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("login.fxml"));
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("main_page.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
