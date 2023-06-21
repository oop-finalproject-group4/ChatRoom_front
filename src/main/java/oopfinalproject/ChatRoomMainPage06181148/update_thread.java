package oopfinalproject.ChatRoomMainPage06181148;

import org.json.JSONArray;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
/**
 * 最麻煩的部分，即時動態更新
 * 
 * 
 * */
public class update_thread extends Thread{
	private boolean running =true;
	public int chatroom_id=-1;
	public PageController page;
	JSONArray prevarr;
	/*
	 * 設定動態更新的目標對象與抓取資料對象，透過room id 實現
	 * */
	public update_thread(PageController inpage,int id) {
		page = inpage;
		chatroom_id = id;

		System.out.println("room ID is "+id);
	};
	// 計數器，避免跟server來回傳太多資料導致server down
	int counter=0;
	@Override
	public void run() {
		System.out.println("Running");

		while (running & page.update_immediate) {
			//決定是否要開跑以及是否有設定update_imm再決定要不要即時更新，以節省AWS的資源
//	        page.stage.setOnCloseRequest(event -> {
//	            // 在關閉 JavaFX 主視窗時，停止自定義的 Thread
//	            running = false;
//	            return;
//	        });
	        counter++;
	        if(counter>100) {
	        	running = false;
	        	return;
	        }
	        
	        // call 更新資料func
			update_Data();
			try {
				System.out.println("Stop Update for 0.3s. name is "+page.username_text);
				//0.3秒更新一次以達到即時更新
				update_thread.sleep(300);
			}catch (Exception e) {
				System.out.println(e.toString());
			}
			
			
		}
	}
	public void stop_update() {
		// 停止更新
		running = false;
		
	}
	public void start_update(PageController in_page,int id) {
		chatroom_id = id;
		page = in_page;
		running = true;
		// 開始更新資料
	}
	JSONArray messages;
	private void update_Data() {
		// 更新資料，透過檢查room id是否存在再去決定是否要繼續跑
		int res = API.viewChatHistory(chatroom_id);
		if(res != 200){
			return;
		}
		
		
		try {
			// 從伺服器端抓取是否有資料更新(訊息更新)
			messages = API.getResultArray();
//		JSONArray messages = API.getResultArray();
			//倘若沒有抓過資料(第一次抓)則不進行判斷
			if(prevarr == null) {
				System.out.println("Prev is Null");
				System.out.println("Testing ");
				prevarr = new JSONArray(messages);                
				Platform.runLater(() -> {
                    // 這裡是 JavaFX Application Thread
                    // 在此執行與 UI 相關的程式碼，需要透過此thread方法去作到避免thread在javafx執行時發生衝突，因為javafx會偵測是否在正常的thread內執行。
					page.chatroom_scrollpane.setVvalue(1);
					System.out.println(page.username_text+"'s Data uploaded.");
                });
			}else {
				if(messages.length()!=(prevarr.length())) {
					//檢查現在的總訊息跟上一次更新的總訊息是否相同，倘若不同才作訊息更新
					//不偵測的話會導致系統崩潰(執行緒太多)
//					System.out.println("Testing ");
					chat_messeage_block msg_block = new chat_messeage_block();
	                Platform.runLater(() -> {
	                    // 這裡是 JavaFX Application Thread
	                	// 將得到的訊息透過近乎與chatroom gen相同的方式更新，但是位於獨立的執行緒內避免導致系統衝突
	                	page.chatroom_messeage_vbox.getChildren().clear();
	                	for(int i= 0; i<messages.length();i++) {
	                		System.out.println("Testing "+ i);
	                		//[{"id":1,"content":"hi","time":"2023-06-14T20:00:25","sender":{"id":"bob","name":"bob"}},{"id":2,"content":"hello?","time":"2023-06-14T20:22:56","sender":{"id":"bob","name":"bob"}},{"id":3,"content":"Hi bob.","time":"2023-06-15T17:23:43","sender":{"id":"alice","name":"alice"}},{"id":4,"content":"Hi Alice, it's bob.","time":"2023-06-16T01:12:07","sender":{"id":"bob","name":"bob"}},{"id":5,"content":"Bob says hi.","time":"2023-06-16T09:39:17","sender":{"id":"bob","name":"bob"}},{"id":6,"content":"I'm getting inpatient here.","time":"2023-06-16T09:55:42","sender":{"id":"bob","name":"bob"}}]
	                		String imagePath = "/img/default_user.png";
	                		String name = messages.getJSONObject(i).getJSONObject("sender").getString("name");
	                		String content = messages.getJSONObject(i).getString("content");
	                		String senderId = messages.getJSONObject(i).getJSONObject("sender").getString("id");
	                		// 創建新資料格
	                		msg_block.add_messeage_block(page.chatroom_messeage_vbox, content,senderId.equals(page.myId));
	                		
	                	}
	                	//下拉
	                	Thread scroll = new Thread(()->{
	                		boolean run = true;
	                		while(run) {
	                			try {
//	                					System.out.println("scrolling to buttom");
	                				Thread.sleep(50);
//	        							page.updateChatroom();
	                				if(page.chatroom_scrollpane.getVvalue()>0.9) {
	                					page.chatroom_scrollpane.setVvalue(1);	                						
	                				}
	                				run = false;
	                			} catch (InterruptedException e) {
	                				// TODO Auto-generated catch block
	                				System.out.println(e.toString());
	                			}
	                		}
	                	});
	                	scroll.start();
	                });
				}else {
					System.out.println("Messeage are same.");
				}
				//設定prevarr以作比對
				prevarr = new JSONArray(messages);
				System.out.println(page.username_text+"'s Data uploaded.");
			}
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
	}
}
