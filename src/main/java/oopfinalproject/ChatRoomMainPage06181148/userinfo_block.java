package oopfinalproject.ChatRoomMainPage06181148;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
public class userinfo_block extends javafx.scene.layout.AnchorPane {
	public userinfo_block() {};
	public void add_userinfo_block(AnchorPane anchorPane,String username,String email,String userid) {
		/*
		 * 透過後端抓取的個人資料，將其資料透過字串形式傳入func中顯示
		 * 
		 * */
		ImageView imageView = new ImageView();
        imageView.setFitHeight(139);
        imageView.setFitWidth(174);
        imageView.setLayoutX(84);
        imageView.setLayoutY(27);
        imageView.setPreserveRatio(true);

        try {
        	imageView.setImage(new Image(getClass().getResource("../img/default_user.png").toExternalForm()));     	
        }catch(Exception e) {
        	System.out.println(e.toString());
        }

        Label nameLabel = createLabel("Name", 24, -1, 166);
        Label userNameLabel = createLabel(username, 24, -1, 211);
        Label userIDLabel = createLabel("UserID", 24, -1, 267);
        Label testUserIDLabel = createLabel(userid, 24, -1, 313);
        Label emailLabel = createLabel("Email", 24, -1, 362);
        Label testEmailLabel = createLabel(email, 24, -1, 413);
        Label systemIDLabel = createLabel("SystemID", 24, -1, 461);


        anchorPane.getChildren().addAll(imageView, nameLabel, userNameLabel, userIDLabel, testUserIDLabel,
                emailLabel, testEmailLabel, systemIDLabel);
	};


    private Label createLabel(String text, double fontSize, double layoutX, double layoutY) {
        Label label = new Label(text);
        label.setFont(new Font(fontSize));
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefWidth(309);
        label.setPrefHeight(32);
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        return label;
    }
}
