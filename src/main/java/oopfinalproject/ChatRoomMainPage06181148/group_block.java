package oopfinalproject.ChatRoomMainPage06181148;
//import javafx.application.Application;
import javafx.geometry.Insets;
//import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
//import javafx.stage.Stage;

public class group_block extends javafx.scene.layout.Pane {
	public group_block(){};
	
	public void add_group_block (VBox target) {
		// 動態設定群組block
		
        Pane pane = new Pane();
        pane.setPrefSize(300, 95);
        HBox hbox = new HBox();
        hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hbox.setPrefSize(310, 95);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
		String workingDir = System.getProperty("user.dir");
		System.out.println("工作位置：" + workingDir);
		String imagePath = "default_user.png";
		try {
			Image temp = (new Image((getClass().getClassLoader().getResourceAsStream(imagePath))));
			imageView.setImage(temp);
		}catch(Exception e) {
			System.out.println(e.toString());
		}

//        imageView.setImage(new Image(getClass().getResourceAsStream(imgloc)));
        VBox vbox = new VBox();
        vbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        vbox.setPrefSize(220, 95);
        Label nameLabel = new Label("testname");
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setPrefWidth(220);
        nameLabel.setFont(new Font(18));
        VBox.setMargin(nameLabel, new Insets(10, 0, 0, 0));
        Label descriptionLabel = new Label("奇萊北峰下屏風的路線規劃的怎麼樣");
//        Label descriptionLabel = new Label(messeage);
        descriptionLabel.setPrefHeight(65);
        descriptionLabel.setPrefWidth(220);
        descriptionLabel.setTextFill(Color.WHITE);
        descriptionLabel.setWrapText(true);
        VBox.setMargin(descriptionLabel, new Insets(10, 0, 0, 0));
        vbox.getChildren().addAll(nameLabel, descriptionLabel);
        hbox.getChildren().addAll(imageView, vbox);
        pane.getChildren().add(hbox);
//        pane.setStyle("-fx-background-color: #FF0000;");
        target.getChildren().add(pane);
//        Scene scene = new Scene(pane);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }
	public void add_group_block (PageController page,VBox target,String name,String messeage,String imgloc,String id) {
		// 透過給定的輸入條件與名稱動態產生並設定群組名稱
		Pane pane = new Pane();
        pane.setPrefSize(300, 95);
        pane.setOnMouseClicked(event->{
        	page.set_chatroom_group(name,id);
        });
        HBox hbox = new HBox();
        hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hbox.setPrefSize(310, 95);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
		String workingDir = System.getProperty("user.dir");
//		System.out.println("工作位置：" + workingDir);
//		String imagePath = "@../img/default_user.png";
		try {
//			System.out.println(getClass().getClassLoader().getResource(imgloc));
//			System.out.println(imgloc);
//			System.out.println(group_block.class.getResource("/"));
//			System.out.println(Thread.currentThread().getContextClassLoader().getResource("/"));
			Image temp = (new Image(imgloc));
			imageView.setImage(temp);
		}catch(Exception e) {
			System.out.println(e.toString());
		}

        VBox vbox = new VBox();
        vbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        vbox.setPrefSize(220, 95);
        Label nameLabel = new Label(name);
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setPrefWidth(220);
        nameLabel.setFont(new Font(18));
        VBox.setMargin(nameLabel, new Insets(10, 0, 0, 0));
        Label descriptionLabel = new Label(messeage);
        descriptionLabel.setPrefHeight(65);
        descriptionLabel.setPrefWidth(220);
        descriptionLabel.setTextFill(Color.WHITE);
        descriptionLabel.setWrapText(true);
        VBox.setMargin(descriptionLabel, new Insets(10, 0, 0, 0));
        vbox.getChildren().addAll(nameLabel, descriptionLabel);
        hbox.getChildren().addAll(imageView, vbox);
        pane.getChildren().add(hbox);
        target.getChildren().add(pane);
    }

}
