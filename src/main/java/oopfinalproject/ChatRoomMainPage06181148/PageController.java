package oopfinalproject.ChatRoomMainPage06181148;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.json.JSONArray;
import org.json.*;

/*
 * 自動更新開啟或關閉記得設定->預設關閉這很重要，倘若要開啟直接到29行開啟即可
 * 多採用純字串形式讓後端抓的資料可以在此頁面進行字串處理後，透過設計好的func interface
 * 將所想要顯示的文字以及狀態放入顯示區塊中，並將ui設定的區域打包好避免麻煩
 * 此為主要控制邏輯區域
 * */


public class PageController {
	// 是否要自動更新，預設關閉，避免過度收發資料導致雲端伺服器用量爆炸。
	boolean update_immediate = false;
	
	// 元件宣告
	private String userId;
	@FXML
	private ScrollPane personalinfo_page;
	@FXML
	private ScrollPane chatroom_page;
	@FXML
	private ScrollPane friend_page;
	@FXML
	private ScrollPane groups_page;
	@FXML
	private ScrollPane addfriend_page;
	@FXML
	private ScrollPane setting_page;
	@FXML
	private ScrollPane addapikey_page;
	@FXML
	private Pane google_apiset_pane;
	@FXML
	private Pane chatgpt_apiset_pane;
	@FXML
	private Pane bing_apiset_pane;
	
    public Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField gpt_read_above;
	
	
	@FXML
	private SplitPane chatroom;
	@FXML
	private Pane google_setting_pane;
	@FXML
	private Pane chatgpt_setting_pane;	
	@FXML
	private Pane bing_setting_pane;
	@FXML
	private Pane add_friend_pane;
	
	@FXML
	private Label chatgpt_apikey_show;
	@FXML
	private Label google_apikey_show;
	@FXML
	private Label bing_apikey_show;
	@FXML
	private TextField google_api_in;
	@FXML
	private TextField chatgpt_api_in;
	@FXML
	private TextField bing_api_in;
	@FXML
	private TextField add_friend_id_in;
	@FXML
	private TextArea messeage_in;
	
	
	@FXML
	private Label searching_id;
	@FXML
	private Label result_id;
	@FXML
	private Label result_name;
	
	@FXML
	public Label chatroom_name = new Label();
	@FXML
	public VBox chatroom_messeage_vbox = new VBox();
	@FXML
	public ScrollPane chatroom_scrollpane = new ScrollPane();
	/*
	private Label Username_userinfo;
	private Label UserID_userinfo;
	private Label Email_userinfo;
	private Label systemid_userinfo;
	private Label Username_setting;
	private Label UserID_setting;
	private Label Email_setting;
	 * */
	@FXML
	public CheckBox updateontime_box;
	public String username_text = "范傑翔";
	public String email_text = "b08209023@ntu.edu.tw";
	public String userid_text = "brainbrian2000";
//	public String systemid_text = "1";
	private String google_api_string;
	private String chatgpt_api_string;
	private String bing_api_string;
	
	
	
	/*
	private void initial_info() {
		Username_userinfo.setText(username_text);
		Username_setting.setText(username_text);
		UserID_userinfo.setText(userid_text);
		UserID_setting.setText(userid_text);
		Email_userinfo.setText(email_text);
		Email_setting.setText(email_text);
		systemid_userinfo.setText(systemid_text);
//		Username_userinfo.
		
	}
	 * */
	
	update_thread thread;
	public String myId;
	private int currentRoomId = -1;

	private String currentFriendId = null;

	@FXML
	public void initialize() {
		// Execute your function when the page is first loaded
		About_You(null);
	}
	public void updateontime(MouseEvent e) {
		// 因為即時更新會使得AWS雲端的收發量大量上漲，導致收費增加，因此選擇開啟或關閉自動更新。
		if(updateontime_box.isSelected()) {
			update_immediate = true;
		}else {
			update_immediate = false;
		}
		
		
	}
	public void updateChatroom() {
		// 設定背景chatroom的相關設計
		VBox inside = new VBox();
		inside.setSpacing(0);
		inside.setStyle("-fx-background-color: #282828;");
		chatroom_page.setContent(inside);
		generate_chatroom_block(inside);
	}
	public void About_You(MouseEvent e) {
		// 設定about you頁面
		// 將該顯示的頁面顯示出來，然後把水平的scrollbar關掉
		System.out.println("Personal Info on Stack");
		personalinfo_page.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		friend_page.setVisible(false);
		chatroom_page.setVisible(false);
		groups_page.setVisible(false);
		addfriend_page.setVisible(false);
		personalinfo_page.setVisible(true);
		setting_page.setVisible(false);
		addapikey_page.setVisible(false);
		personalinfo_page.setContent(null);
		chatroom.setVisible(true);
		add_friend_pane.setVisible(false);
		try {
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(false);
		}catch (Exception err) {
			System.out.println(err.toString());
		}
		// 設定基本頁面、背景畫面
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(307, 597);
        anchorPane.setStyle("-fx-background-color: #282828;");
		try {
			// 調整scrollbar的樣子，不要用預設的樣子，很醜
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
			personalinfo_page.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		personalinfo_page.setContent(anchorPane);
		userinfo_block block = new userinfo_block();
		//200 {"id":"bob","name":"bob","password":"87654321","email":"bob@gmail.com","apikey":"someRandomKey"}
		//獲得個人資料
		API.getInfo();
		username_text = API.getResultObject().getString("name");
		email_text = API.getResultObject().getString("email");
		userid_text = API.getResultObject().getString("id");
		myId = userid_text;
		block.add_userinfo_block(anchorPane,username_text,email_text,userid_text);
		// 將資料讀出並用函數的形式輸出於user info block
		messeage_in.requestFocus();
		// 將輸入游標預設設定於message block中
		try {
			thread.stop_update();    		
		}catch (Exception error_of_thread) {
			System.out.println(error_of_thread.toString());
		}
		// 關閉執行緒，避免耗用太多資源。
		
	}
	public void Chat_Room(MouseEvent e) {
		// 關閉非必要的頁面
		System.out.println("Chat room on Stack");
		friend_page.setVisible(false);
		chatroom_page.setVisible(true);
		groups_page.setVisible(false);
		addapikey_page.setVisible(false);
		addfriend_page.setVisible(false);
		personalinfo_page.setVisible(false);
		setting_page.setVisible(false);
		chatroom.setVisible(true);
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(false);
		add_friend_pane.setVisible(false);
		chatroom_page.setContent(null);
		chatroom_page.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// 同上，設定頁面與滑鼠
		try {
			chatroom_page.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		// 將設計之頁面透過vbox設定
		// 並透過call外部函數去將物件生成進入vbox裡面，做到動態生成
		VBox inside = new VBox();
		inside.setSpacing(0);
		inside.setStyle("-fx-background-color: #282828;");
		chatroom_page.setContent(inside);
		generate_chatroom_block(inside);
		messeage_in.requestFocus();

	}
	public void Friends(MouseEvent e) {
		// 關閉非必要的頁面
		System.out.println("Friends on Stack ");
		friend_page.setVisible(true);
		chatroom_page.setVisible(false);
		groups_page.setVisible(false);
		friend_page.setContent(null);
		addfriend_page.setVisible(false);
		personalinfo_page.setVisible(false);
		setting_page.setVisible(false);
		addapikey_page.setVisible(false);
		chatroom.setVisible(true);
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(false);
		add_friend_pane.setVisible(false);
		VBox inside = new VBox();
		inside.setSpacing(0);
		inside.setStyle("-fx-background-color: #282828;");
		friend_page.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// 同上，設定頁面與滑鼠
		try {
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
			friend_page.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		// 將設計之頁面透過vbox設定
		// 並透過call外部函數去將物件生成進入vbox裡面，做到動態生成
		friend_page.setContent(inside);
		generate_friend_block(inside);
		messeage_in.requestFocus();

	}
	public void Groups(MouseEvent e) {
		// 關閉非必要的頁面
		System.out.println("Groups on Stack");
		friend_page.setVisible(false);
		chatroom_page.setVisible(false);
		groups_page.setVisible(true);
		addapikey_page.setVisible(false);
		chatroom.setVisible(true);
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(false);
		add_friend_pane.setVisible(false);
		VBox inside = new VBox();
		inside.setSpacing(0);
		inside.setStyle("-fx-background-color: #282828;");
		groups_page.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// 同上，設定頁面與滑鼠
		try {
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
			groups_page.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		// 將設計之頁面透過vbox設定
		// 並透過call外部函數去將物件生成進入vbox裡面，做到動態生成
		groups_page.setContent(inside);
		generate_group_block(inside);
		addfriend_page.setVisible(false);
		personalinfo_page.setVisible(false);
		setting_page.setVisible(false);
		messeage_in.requestFocus();
		try {
			thread.stop_update();    		
		}catch (Exception error_of_thread) {
			System.out.println(error_of_thread.toString());
		}
	}
	public void Add_Friend(MouseEvent e) {
		// 關閉非必要頁面
		System.out.println("Add Friend on Stack");
		friend_page.setVisible(false);
		chatroom_page.setVisible(false);
		groups_page.setVisible(false);
		addfriend_page.setVisible(true);
		personalinfo_page.setVisible(false);
		setting_page.setVisible(false);
		addapikey_page.setVisible(false);
		chatroom.setVisible(false);
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(false);
		add_friend_pane.setVisible(true);
		VBox inside = new VBox();
		inside.setSpacing(0);
		inside.setStyle("-fx-background-color: #282828;");
		addfriend_page.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// 設定頁面與滑鼠還有整體顯示外表
		try {
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
			addfriend_page.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		addfriend_page.setContent(inside);
		generate_friend_block(inside);
		// 用函數動態生成內部物件
		try {
			thread.stop_update();    		
		}catch (Exception error_of_thread) {
			System.out.println(error_of_thread.toString());
		}
		// 停止thread
	}
	public void Add_API_Key(MouseEvent e) {
		System.out.println("Add API Key on Stack");
		friend_page.setVisible(false);
		chatroom_page.setVisible(false);
		groups_page.setVisible(false);
		addfriend_page.setVisible(false);
		personalinfo_page.setVisible(false);
		setting_page.setVisible(false);
		addapikey_page.setVisible(true);
		add_friend_pane.setVisible(false);
		addapikey_page.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// 設定頁面
		try {
			thread.stop_update();    		
		}catch (Exception error_of_thread) {
			System.out.println(error_of_thread.toString());
		}
	}
	
	@FXML
	private ChoiceBox<String> choicing;
	@FXML
	private TextField setting_persional_info_input;
	
	public void Setting(MouseEvent e) {
		//設定是否可視
		chatroom.setVisible(true);
		System.out.println("Setting on Stack");
		friend_page.setVisible(false);
		chatroom_page.setVisible(false);
		groups_page.setVisible(false);
		addfriend_page.setVisible(false);
		personalinfo_page.setVisible(false);
		setting_page.setVisible(true);
		addapikey_page.setVisible(false);
		add_friend_pane.setVisible(false);
		setting_page.setContent(null);
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(false);
		add_friend_pane.setVisible(false);
        //設定動態生成頁面
		AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(290, 597);
        anchorPane.setStyle("-fx-background-color: #282828;");
		try {
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
			setting_page.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		setting_page.setContent(anchorPane);
		setting_block block = new setting_block();
		block.add_setting_block(anchorPane,username_text,email_text,userid_text);
		choicing.getItems().setAll("NaN","Name", "ID","Email");
		choicing.setValue("NaN");
		choicing.setLayoutX(75);
		choicing.setLayoutY(460);
		choicing.setPrefWidth(150);
		choicing.setPrefHeight(32);
		setting_persional_info_input.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		setting_persional_info_input.setLayoutX(4);
		setting_persional_info_input.setLayoutY(514);
		setting_persional_info_input.setPrefWidth(290);
		setting_persional_info_input.setPrefHeight(32);
		anchorPane.getChildren().addAll(choicing,setting_persional_info_input);
		setting_persional_info_input.requestFocus();
		//生成動態狀態的顯示物件
		
	}
	/*
	public void generate_chatroom_block(VBox inside) {
		chatroom_block element = new chatroom_block();
		System.out.println("Groups is generate ");
		// 200 [
		// {"id":1,"roomName":"alice","participant1":{"id":"alice","name":"alice"},"participant2":{"id":"bob","name":"bob"},"group":false}
		// , ...]
		API.viewAllChatroom();
		JSONArray rooms = API.getResultArray();
		for(int i= 0; i<rooms.length();i++) {
//			System.out.println("Testing "+ i);
			String imagePath = "/img/default_user.png";
			int id = rooms.getJSONObject(i).getInt("id");
			String name = rooms.getJSONObject(i).getString("roomName");
//			element.add_group_block(inside,name,messeage,imagePath);
			element.add_chatroom_block(this,inside,name,"",imagePath,Integer.toString(id));
			inside.getChildren().add(element);
		}
	}
	*/
	public void generate_chatroom_block(VBox inside) {
		/**
		 * 生成不同聊天室的物件
		 * */
		// 設定基本物件高度，避免高度太小
		inside.setMinHeight(600);
		  chatroom_block element = new chatroom_block();
		  System.out.println("Groups is generate ");
		  // 200 [
		  // {"id":1,"roomName":"alice","participant1":{"id":"alice","name":"alice"},"participant2":{"id":"bob","name":"bob"},"group":false}
		  // , ...]
		  API.viewAllChatroom();
		  //抓取chatroom 的資料
		  JSONArray rooms = API.getResultArray();
		  //轉成array
		  for(int i= 0; i<rooms.length();i++) {
		//   System.out.println("Testing "+ i);
		   String imagePath = "/img/default_user.png";
		   int id = rooms.getJSONObject(i).getInt("id");
		   String name = rooms.getJSONObject(i).getString("roomName");
		   String message =  "";
		   //轉成字串形式
		   try{
			   //讀取最後的訊息，並在等等傳入生成block中
		    message = rooms.getJSONObject(i).getString("lastMessage");
		    System.out.print("Last message "+message);
		   }catch (Exception e){}
//		   element.add_group_block(inside,name,messeage,imagePath);
		   // 將得到的資料生成聊天block並以函數形式動態生成block
		   element.add_chatroom_block(this,inside,name,message,imagePath,Integer.toString(id));
//		   inside.getChildren().add(element);
		  }
		  //將聊天室聊天內容往下拉
	    	Thread scroll = new Thread(()->{
	    		boolean run = true;
	    		while(run) {
		    		try {
//		    			System.out.println("scrolling to buttom");
						Thread.sleep(50);
						chatroom_scrollpane.setVvalue(1);
						run = false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println(e.toString());
					}
	    		}
	    	});
	    	scroll.start();
		 }
	
	public void generate_group_block(VBox inside) {
		group_block element = new group_block();
		inside.setMinHeight(600);
		System.out.println("Groups is generate ");
		for(int i= 0; i<10;i++) {
//			System.out.println("Testing "+ i);
			String imagePath = "/img/default_user.png";
			String id = "id_group"+i;
//			element.add_group_block(inside,name,messeage,imagePath);
			element.add_group_block(this,inside,"name_group"+i,"messeage_"+i,imagePath,id);
//			inside.getChildren().add(element);
		}
	}
	public void generate_friend_block(VBox inside) {
		//方法同生成聊天室對話框
		inside.setMinHeight(600);
		friend_block element = new friend_block();
//		System.out.println("Groups is generate ");
		API.viewFriend();
		JSONArray friends = API.getResultArray();
		for(int i= 0; i<friends.length();i++) {
			String imagePath = "../img/default_user.png";
			String id = friends.getJSONObject(i).getString("id");
			String name = friends.getJSONObject(i).getString("name");
//			element.add_group_block(inside,name,messeage,imagePath);
			element.add_friend_block(this,inside,name,imagePath,id);
		}
	}

	public void google_setting(MouseEvent e) {
		// 設定api頁面
		chatroom.setVisible(false);
		google_setting_pane.setVisible(true);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(false);
		add_friend_pane.setVisible(false);
		google_api_in.requestFocus();
	}
	public void chatgpt_setting(MouseEvent e) {
		//設定api頁面
		chatroom.setVisible(false);
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(true);
		bing_setting_pane.setVisible(false);
		add_friend_pane.setVisible(false);
		chatgpt_api_in.requestFocus();
	}
	public void bing_setting(MouseEvent e) {
		//設定api頁面
		chatroom.setVisible(false);
		google_setting_pane.setVisible(false);
		chatgpt_setting_pane.setVisible(false);
		bing_setting_pane.setVisible(true);
		add_friend_pane.setVisible(false);
		bing_api_in.requestFocus();
	}
	public void google_api_enter(KeyEvent event){
        if (event.getCode().toString().equals("ENTER")) {
            // 當設定api輸入進去後將api更新到程式內部
            System.out.println("Enter key pressed");
            String temp = "";
            temp = google_api_in.getText();
            google_api_string = temp;
            google_apikey_show.setText(google_api_string);
            google_api_in.clear();
        }
    };
	public void bing_api_enter(KeyEvent event){
        if (event.getCode().toString().equals("ENTER")) {
            // 當設定api輸入進去後將api更新到程式內部
            System.out.println("Enter key pressed");
            String temp = "";
            temp = bing_api_in.getText();
            bing_api_string = temp;
            bing_apikey_show.setText(bing_api_string);
            bing_api_in.clear();
        }
    };
	public void chatgpt_api_enter(KeyEvent event){
        if (event.getCode().toString().equals("ENTER")) {
        	//當設定api輸入進去後將api更新到程式內部
        	System.out.println("Enter key pressed");
            String temp = "";
            temp = chatgpt_api_in.getText();
            chatgpt_api_string = temp;
            chatgpt_apikey_show.setText(chatgpt_api_string);
            chatgpt_api_in.clear();
        }
    };
	public void add_friend_id_enter(KeyEvent event){
		 if (event.getCode().toString().equals("ENTER")) {
	            // 開始搜尋朋友，當是enter的時候開始搜尋
	            System.out.println("Enter key pressed");
	            String temp = "";
	            temp = add_friend_id_in.getText();
	            //searching_id.setText(temp);
	            //searching method//
	            //用api搜尋朋友
	            int resCode = API.searchFriend(temp);
	            if(resCode == 200) {
	                userId = temp;
	                result_id.setText(temp);
	                result_name.setText(temp); 
	                add_friend_id_in.clear();
	            }
	            else {
	                result_id.setText("NAN");
	                result_name.setText("NAN"); 
	            }

	            //print result//
//	          result_id.setText(temp);
//	          result_name.setText(temp);

	 //         add_friend_id_in.clear();
	        }
    };    
    public void add_freind_check_buttom(MouseEvent e) {
    	// 將搜尋到的朋友加入
        searching_id.setText(null);
        result_id.setText(null);
        result_name.setText(null);
        String resultid = userId;
        int resCode = API.addFriend(resultid);
        System.out.println(resCode);
        //add into data base and show in friend page//
        Add_Friend(e);
    	
    }
//	@SuppressWarnings("exports")
	public void setting_persional_info_enter(KeyEvent event) {
		// 設定個人資料，但後端沒有實作這部分因此只會在前端顯示，爾後若有新功能可以加入此區塊
        if (event.getCode().toString().equals("ENTER")) {
            System.out.println("Enter key pressed");
            String temp = "";
            String selected_update = choicing.getValue();
            temp = setting_persional_info_input.getText();
            if(selected_update.equals("NaN")) {
            	System.out.println("NaN been choose.");
            }else if (selected_update.equals("Name")) {
            	System.out.println("Name been choose.");
            	username_text = temp;
            }else if (selected_update.equals("ID")) {
            	System.out.println("ID been choose.");
            	userid_text = temp;
            }else if (selected_update.equals("Email")) {
            	System.out.println("Email been choose.");
            	if(temp.contains("@")) {
            		email_text = temp;
            	}else {
            		System.out.println("Not value address");
            	}
            }
            setting_persional_info_input.clear();
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(290, 597);
            anchorPane.setStyle("-fx-background-color: #282828;");
            setting_page.setContent(anchorPane);
            setting_block block = new setting_block();
            block.add_setting_block(anchorPane,username_text,email_text,userid_text);	
            choicing.getItems().setAll("NaN","Name", "ID","Email");
            choicing.setValue("NaN");
            choicing.setLayoutX(75);
            choicing.setLayoutY(460);
            choicing.setPrefWidth(150);
            choicing.setPrefHeight(32);
            setting_persional_info_input.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            setting_persional_info_input.setLayoutX(4);
            setting_persional_info_input.setLayoutY(514);
            setting_persional_info_input.setPrefWidth(290);
            setting_persional_info_input.setPrefHeight(32);
            anchorPane.getChildren().addAll(choicing,setting_persional_info_input);
        }
        
		
		
	}

	public void generate_message_block(String friendId){
		//產生messeage 訊息格
		chatroom_messeage_vbox.getChildren().clear();
		try {
			chatroom_scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		chat_messeage_block msg_block = new chat_messeage_block();
		int res = API.viewChatHistoryByFriend(friendId);
		//透過friend產生訊息格，特別適用於沒有聊過天的朋友
		if(res != 200){
			return;
		}
		// 得到message
		JSONArray messages = API.getResultArray();
		//[{"id":1,"content":"hi","time":"2023-06-14T20:00:25","sender":{"id":"bob","name":"bob"}},{"id":2,"content":"hello?","time":"2023-06-14T20:22:56","sender":{"id":"bob","name":"bob"}},{"id":3,"content":"Hi bob.","time":"2023-06-15T17:23:43","sender":{"id":"alice","name":"alice"}},{"id":4,"content":"Hi Alice, it's bob.","time":"2023-06-16T01:12:07","sender":{"id":"bob","name":"bob"}},{"id":5,"content":"Bob says hi.","time":"2023-06-16T09:39:17","sender":{"id":"bob","name":"bob"}},{"id":6,"content":"I'm getting inpatient here.","time":"2023-06-16T09:55:42","sender":{"id":"bob","name":"bob"}}]
		for(int i= 0; i<messages.length();i++) {
//			System.out.println("Testing "+ i);
			String imagePath = "/img/default_user.png";
			String name = messages.getJSONObject(i).getJSONObject("sender").getString("name");
			String content = messages.getJSONObject(i).getString("content");
			String senderId = messages.getJSONObject(i).getJSONObject("sender").getString("id");
			msg_block.add_messeage_block(chatroom_messeage_vbox, content,!senderId.equals(friendId));
			//將得到的訊息一一顯示於視窗
		}
		Thread scroll = new Thread(()->{
			try {
//				System.out.println("scrolling to buttom");
				Thread.sleep(50);
					chatroom_scrollpane.setVvalue(1);	                						
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		});
		// 將視窗拉到最下面
		scroll.start();
	};

	public void generate_message_block(int roomId){
		// 透過room id 從chat room頁面點擊時會回報(更新)room id，透過此id去抓取聊天紀錄 
		chatroom_messeage_vbox.getChildren().clear();
		try {
			chatroom_scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			chatroom_scrollpane.getStylesheets().add(this.getClass().getResource("scrollbar_style.css").toExternalForm());
		}catch(Exception err) {
			System.out.println(err.toString());
		}
		// 用api抓取紀錄，倘若error則結束此問題
		int res = API.viewChatHistory(roomId);
		if(res != 200){
			return;
		}
		// 
		JSONArray messages = API.getResultArray();
		//[{"id":1,"content":"hi","time":"2023-06-14T20:00:25","sender":{"id":"bob","name":"bob"}},{"id":2,"content":"hello?","time":"2023-06-14T20:22:56","sender":{"id":"bob","name":"bob"}},{"id":3,"content":"Hi bob.","time":"2023-06-15T17:23:43","sender":{"id":"alice","name":"alice"}},{"id":4,"content":"Hi Alice, it's bob.","time":"2023-06-16T01:12:07","sender":{"id":"bob","name":"bob"}},{"id":5,"content":"Bob says hi.","time":"2023-06-16T09:39:17","sender":{"id":"bob","name":"bob"}},{"id":6,"content":"I'm getting inpatient here.","time":"2023-06-16T09:55:42","sender":{"id":"bob","name":"bob"}}]
		chat_messeage_block msg_block = new chat_messeage_block();
		for(int i= 0; i<messages.length();i++) {
			String imagePath = "/img/default_user.png";
			String name = messages.getJSONObject(i).getJSONObject("sender").getString("name");
			String content = messages.getJSONObject(i).getString("content");
			String senderId = messages.getJSONObject(i).getJSONObject("sender").getString("id");
			msg_block.add_messeage_block(chatroom_messeage_vbox, content,senderId.equals(myId));
		}
		Thread scroll = new Thread(()->{
			try {
//				System.out.println("scrolling to buttom");
				Thread.sleep(150);
					chatroom_scrollpane.setVvalue(1);	                						
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}
		});
		// 將視窗拉到最下面
		scroll.start();
	};
///*
	
	String temp = "";
	public void messeage_in_enter(KeyEvent event){
		//傳訊息
		temp = "";
		
		messeage_in.setWrapText(true);
        if (event.getCode().toString().equals("ENTER")) {
            //如果只有enter則不將該enter作為訊息傳出
        	temp =messeage_in.getText();
            if(temp.equals("\n")) {
            	messeage_in.clear();
            	return;
            }else {
            	//將最後的enter去掉
            	temp =temp.substring(0, temp.length() - 1);
            	temp = temp.replaceAll("\"", "");
            	//移除會讓系統爆炸的字元
            	System.out.println(temp);
//            	temp = temp.replaceAll("\\", "");
            	chat_messeage_block msg_block = new chat_messeage_block();
            	//動態創建新訊息格
				if(currentRoomId != -1){            
					new Thread(() -> {
						API.messageRoom(currentRoomId, temp);
						//先傳訊息到後端，因為傳訊息會有時間delay所以透過thread爾後更新
						Platform.runLater(() -> {
							generate_message_block(currentRoomId);		
							VBox inside = new VBox();
							inside.setSpacing(0);
							inside.setStyle("-fx-background-color: #282828;");
							chatroom_page.setContent(inside);
							generate_chatroom_block(inside);
							// 傳完訊息後再更新一次聊天室，確認訊息有發出去的話會常態顯示
			            	Thread scroll = new Thread(()->{
		        	    		try {
//		        	    			System.out.println("scrolling to buttom");
		        					Thread.sleep(50);
		        					chatroom_scrollpane.setVvalue(1);
		        				} catch (InterruptedException e) {
		        					// TODO Auto-generated catch block
		        					System.out.println(e.toString());
		        				}
			            	});
		        			scroll.start();
						});
					}).start();
					msg_block.add_messeage_block(chatroom_messeage_vbox, temp,true);
					//透過前一個thread將發送到後端的訊息跟本機端的訊息顯示分隔開，達到類似於即時傳訊息的動態效果
	            	Thread scroll = new Thread(()->{
        	    		try {
//        	    			System.out.println("scrolling to buttom");
        					Thread.sleep(50);
        					chatroom_scrollpane.setVvalue(1);
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					System.out.println(e.toString());
        				}
	            	});
        			scroll.start();
				}else if (currentFriendId != null) {
//					generate_message_block(currentFriendId);
//					API.messageRoom(currentRoomId, temp);
					new Thread(() -> {
						API.messageFriend(currentFriendId, temp);
						Platform.runLater(() -> {
							generate_message_block(currentRoomId);	
							VBox inside = new VBox();
							inside.setSpacing(0);
							inside.setStyle("-fx-background-color: #282828;");
							chatroom_page.setContent(inside);
							generate_chatroom_block(inside);
						});
					}).start();
					msg_block.add_messeage_block(chatroom_messeage_vbox, temp,true);
	            	Thread scroll = new Thread(()->{
        	    		try {
//        	    			System.out.println("scrolling to buttom");
        					Thread.sleep(50);
        					chatroom_scrollpane.setVvalue(1);
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					System.out.println(e.toString());
        				}
	            	});
        			scroll.start();
				}else if(currentRoomId == -1) {
					msg_block.add_messeage_block(chatroom_messeage_vbox, temp,true);
	            	msg_block.add_messeage_block(chatroom_messeage_vbox, temp,false);
	            	Thread scroll = new Thread(()->{
        	    		try {
//        	    			System.out.println("scrolling to buttom");
        					Thread.sleep(50);
        					chatroom_scrollpane.setVvalue(1);
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					System.out.println(e.toString());
        				}
	            	});
        			scroll.start();

				}

				messeage_in.clear();
            }

        }
//        chatroom_scrollpane.setVvalue(1);

    };  
    
// * */
    /*
    public void messeage_in_enter(KeyEvent event){
    	  messeage_in.setWrapText(true);
    	        if (event.getCode().toString().equals("ENTER")) {
    	            String temp = "";
    	            temp =messeage_in.getText();
    	            if(temp.equals("\n")) {
    	             messeage_in.clear();
    	             return;
	            }else {
	    	    String content = temp.substring(0, temp.length() - 1);
	    	    if (currentRoomId != -1) {
		    	    chat_messeage_block msg_block = new chat_messeage_block();
		    	    msg_block.add_messeage_block(chatroom_messeage_vbox, temp, true);
		            // Create a new thread for the API call
		            Thread apiThread = new Thread(() -> {
		            	API.messageRoom(currentRoomId, content);
		            	// Perform any additional actions after the API call if needed
//		            	 generate_message_block(currentRoomId);
		            	Chat_Room(null);
		            });
		            chatroom_scrollpane.setVvalue(1);


    	     apiThread.start(); // Start the thread to make the API call
    	    } else if (currentFriendId != null) {
	    	    chat_messeage_block msg_block = new chat_messeage_block();
	    	    msg_block.add_messeage_block(chatroom_messeage_vbox, temp, true);
	    	    // Create a new thread for the API call
	    	    Thread apiThread = new Thread(() -> {
		    	    API.messageFriend(currentFriendId, content);
		    	    // Perform any additional actions after the API call if needed
//		    	     generate_message_block(currentFriendId);
		    	    Chat_Room(null);
	    	    });
	    	    chatroom_scrollpane.setVvalue(1);
	
	    	    apiThread.start(); // Start the thread to make the API call
    	    } else if(currentRoomId == -1) {
	        	chat_messeage_block msg_block = new chat_messeage_block();
				msg_block.add_messeage_block(chatroom_messeage_vbox, temp,true);
            	msg_block.add_messeage_block(chatroom_messeage_vbox, temp,false);
        		chatroom_scrollpane.setVvalue(1);

			}

			messeage_in.clear();
        }

    }
    chatroom_scrollpane.setVvalue(1);

    	    messeage_in.clear();
    	            }
    	        }

    	    };
    
//    */
    public void set_chatroom_chatroom(String name,String id) {
    	// 進入聊天室後，從chatroom生成時透過此函數去call即時更新功能，使用thread實現即時更新(chatroom block 物件會call此函數更新在此頁面，因為該thread操作的對象是主頁面，因此要寫在main controller權限才夠)
    	System.out.println("Changing Chat Room Friend");
    	System.out.println(name);
    	System.out.println(id);
    	try {
    		//先關閉thread，避免資源多載
    		thread.stop_update();    		
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    	// 如果設定及時新，在開始跑即時更新功能
    	currentRoomId = Integer.parseInt(id);
    	if(update_immediate) {
    		
	    	try {
	    		thread= new update_thread(this, currentRoomId);
	    		thread.start_update(this, currentRoomId);
	    		thread.start();
	    		// 設定即時更新的執行緒並開始工作
	    	}catch (Exception e) {
	    		System.out.println(e.toString());
	    	}
    	}
	    // 將聊天室從朋友狀態切為聊天室狀態
		currentFriendId = null;
    	try {
    		// 設定聊天室名稱
			int roomId = Integer.parseInt(id);
			generate_message_block(roomId);
			chatroom_name.setText(name);
		} catch (Exception e) {
			 //TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
    	// 下拉
    	Thread scroll = new Thread(()->{
    		boolean run = true;
    		while(run) {
	    		try {
//	    			System.out.println("scrolling to buttom");
					Thread.sleep(150);
					chatroom_scrollpane.setVvalue(1);
					run = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				}
    		}
    	});
    	scroll.start();
    	
    	messeage_in.requestFocus();
    }
    
    public void set_chatroom_friend(String name,String id) {
    	// 設定從朋友方法進入的聊天室
    	System.out.println("Changing Chat Room Friend");
    	System.out.println(name);
    	System.out.println(id);
		currentFriendId = id;
		currentRoomId = -1;
    	try {
			generate_message_block(id);
			chatroom_name.setText(name);
		} catch (Exception e) {
			 //TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
    	Thread scroll = new Thread(()->{
    		boolean run = true;
    		while(run) {
	    		try {
//	    			System.out.println("scrolling to buttom");
					Thread.sleep(150);
					chatroom_scrollpane.setVvalue(1);
					run = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				}
    		}
    	});
    	scroll.start();
    }
    
    public void set_chatroom_group(String name,String id) {
    	System.out.println("Changing Chat Room Group");
    	System.out.println(name);
    	System.out.println(id);
    	try {
    		
			chatroom_name.setText(name);
		} catch (Exception e) {
			 //TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
    }
    
    
    public void logout(MouseEvent event)throws IOException {
    	// 登出，並跳轉回主畫面
        API.logout();
        try {
    		thread.stop_update();    		
    	}catch (Exception error_of_thread) {
    		System.out.println(error_of_thread.toString());
    	}
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    int read_msg = 3;
    public void chatgpt_reply(MouseEvent event) {
    	//設定chatgpt的自動生成功能
    	chatgpt_extend gpt = new chatgpt_extend();
//        TextFormatter<String> formatter = new TextFormatter<>(change -> {
//            if (!change.getText().matches("\\d*")) {
//                read_msg = 3;
//            	return null;
//                
//            }
//            return change;
//        });
//        gpt_read_above.setTextFormatter(formatter);
    	// 設定向上讀取幾則訊息，倘若生成失敗則讀三則
    	try{int temp = Integer.parseInt(gpt_read_above.getText());
    		read_msg = temp;
    	}
    	catch (Exception errormsg) {
    		System.out.println(errormsg.toString());
    	}
    	
    	//讀取訊息
    	String reply_gpt="",err;
    	//從後端抓取聊天歷史紀錄
		int res = API.viewChatHistory(currentRoomId);
		if(res != 200){
			return;
		}
		JSONArray messages = API.getResultArray();
		//[{"id":1,"content":"hi","time":"2023-06-14T20:00:25","sender":{"id":"bob","name":"bob"}},{"id":2,"content":"hello?","time":"2023-06-14T20:22:56","sender":{"id":"bob","name":"bob"}},{"id":3,"content":"Hi bob.","time":"2023-06-15T17:23:43","sender":{"id":"alice","name":"alice"}},{"id":4,"content":"Hi Alice, it's bob.","time":"2023-06-16T01:12:07","sender":{"id":"bob","name":"bob"}},{"id":5,"content":"Bob says hi.","time":"2023-06-16T09:39:17","sender":{"id":"bob","name":"bob"}},{"id":6,"content":"I'm getting inpatient here.","time":"2023-06-16T09:55:42","sender":{"id":"bob","name":"bob"}}]
//		chat_messeage_block msg_block = new chat_messeage_block();
		JSONArray arr = new JSONArray();
		//並根據設定的訊息量網回讀取，倘若訊息量不足則只讀到可讀的範圍
		if(messages.length()<read_msg) {
			for(int i= 0; i<messages.length();i++) {
				arr.put(messages.getJSONObject(i));
			}
		}else {
			for(int i= messages.length()-read_msg; i<messages.length();i++) {
				arr.put(messages.getJSONObject(i));
			}
		}
		//將讀到的訊息已json->String的形式傳到chatgpt上讀取
    	try{
    		reply_gpt = gpt.getChat(arr.toString(),chatgpt_api_string);
    	}catch(Exception e) {
    		err = e.toString();
    		messeage_in.setPromptText(err);
    		//將讀取到的error顯示在輸入介面上
    	}
    	//倘若沒有成功讀取則會回傳no message，就不顯示
    	if(!reply_gpt.equals("No message.")) {
    		reply_gpt = reply_gpt.substring(reply_gpt.indexOf(':')+1);
    		//將讀取到的資料設定於聊天視窗中。
    		messeage_in.setText(reply_gpt);    		
    	}
    }
}
