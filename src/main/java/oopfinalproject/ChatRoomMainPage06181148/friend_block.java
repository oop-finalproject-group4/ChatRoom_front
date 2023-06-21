package oopfinalproject.ChatRoomMainPage06181148;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class friend_block extends javafx.scene.layout.Pane {
	public friend_block(){};
	PageController for_call_func = new PageController();
	String name_inside,id_inside;
	public void add_friend_block (VBox target) {
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
        
        try{
        	imageView.setImage(new Image(getClass().getResourceAsStream("/img/default_user.png")));
        }catch(Exception e) {
        	System.out.println(e.toString());
        }
        Label nameLabel = new Label("張譯心");
        nameLabel.setPrefWidth(220);
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(new Font(18));
        nameLabel.setPadding(new Insets(0, 0, 0, 20));

        hBox.getChildren().addAll(imageView, nameLabel);
        target.getChildren().add(pane);
    }
	public void add_friend_block (PageController page,VBox target,String name,String imgloc,String id) {
		//透過給定的參數動態生成朋友顯示塊
        Pane pane = new Pane();
        pane.setOnMouseClicked(event->{
        	page.set_chatroom_friend(name,id);
        });
        pane.setPrefSize(300, 95);
//        name_inside = name;
//        id_inside = 
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
        // 將顯示塊以多層形式加入pane中，爾後放入vbox中
        hBox.getChildren().addAll(imageView, nameLabel);
        target.getChildren().add(pane);
    }

}