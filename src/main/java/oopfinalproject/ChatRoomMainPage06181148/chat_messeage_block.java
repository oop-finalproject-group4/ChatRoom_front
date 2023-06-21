package oopfinalproject.ChatRoomMainPage06181148;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
//import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class chat_messeage_block extends javafx.scene.layout.Pane {
	public chat_messeage_block(){};
	
	public void add_messeage_block (VBox target,String messeage,boolean me) {
		// 讀取資料，並生成訊息格
		// 根據訊息，欲設定在哪邊(聊天室窗)，以及是否為自己傳的訊息進行判別
        FlowPane pane = new FlowPane();
        pane.setStyle("-fx-background-color: #353535;");
        if(me) {
        	//決定要將聊天block設定在右側或左側
        	pane.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        }else {
        	pane.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        	
        }
        pane.setPrefWidth(300);
        TextFlow msg = new TextFlow();
        Text msg_inside = new Text(messeage);
        Region smoothRectangle = new Region();
//        smoothRectangle.setPrefWidth(250)nd-color: #27374D; -fx-background-radius: 10px;");
        smoothRectangle.setStyle("-fx-background-color: #424242; -fx-background-radius: 20px;");
        msg_inside.setFill(Color.WHITE);
        msg.setTextAlignment(TextAlignment.LEFT);
        msg.setMaxWidth(250);
        msg.getChildren().add(msg_inside);
        // 生成資料格並按照欲呈現的形式設定
        // 我設定這塊區域設定到快瘋掉，動態fitting有夠煩人，CSS真的讓人崩潰
        StackPane stack = new StackPane();
        StackPane stack2 = new StackPane();
        stack2.getChildren().add(msg);
        msg.setPadding(new javafx.geometry.Insets(10,10,10,10));
        stack2.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        stack.getChildren().addAll(smoothRectangle,msg);
        stack.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        stack2.setPadding(new javafx.geometry.Insets(10,10,10,10));
        stack.setPadding(new javafx.geometry.Insets(5,10,5,10));
        pane.getChildren().add(stack);
        pane.setPadding(new javafx.geometry.Insets(5,10,0,5));
        target.getChildren().add(pane);
//        pane.setPrefSize(300,);
    }

	
	
	
	
	
	
	
	public void add_friend_block (VBox target,String name,String imgloc) {
		//for testing and debug
        Pane pane = new Pane();
        pane.setPrefSize(300, 95);

        HBox hBox = new HBox();
        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setPrefSize(310, 95);
        pane.getChildren().add(hBox);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(37);
        imageView.setFitWidth(50);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
		try {
			Image temp = (new Image(imgloc));
			imageView.setImage(temp);
		}catch(Exception e) {
			System.out.println(e.toString());
		}

        Label nameLabel = new Label(name);
        nameLabel.setPrefWidth(220);
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(new Font(18));
        nameLabel.setPadding(new Insets(0, 0, 0, 20));

        hBox.getChildren().addAll(imageView, nameLabel);
        target.getChildren().add(pane);
    }

}