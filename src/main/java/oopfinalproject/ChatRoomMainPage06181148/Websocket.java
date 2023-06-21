package oopfinalproject.ChatRoomMainPage06181148;

import javafx.application.Platform;
import org.json.JSONArray;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import java.util.concurrent.Future;
import java.lang.reflect.Type;

public class Websocket {
    private static StompSession session;
    //Websocket連線請與登入登出同步 （意外關閉沒關係）
    //與websocket建立連線，不需改動，但請在API.login後再呼叫
    public static void connectionWebsocket(PageController page){
        try{
            WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());

            StompSessionHandler sessionHandler = new MyStompSessionHandler();

            String url = "ws://localhost:8080/ws";
            session = stompClient.connect(url, sessionHandler).get();
            // Send a message
            String destination = "/app/login";

            session.send(destination, new  Auth(API.getId(), API.getToken()));
            String notificationTopic = "/topic/notifications/" + API.getToken();
            System.out.println(notificationTopic);
            session.subscribe(notificationTopic, new MyStompFrameHandler(page));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //切斷連線（登出時呼叫），請先呼叫API.logout，接著再disconnectWebsocket
    public static void disconnectWebsocket(){
        session.disconnect();
    }

    public static class MyStompFrameHandler implements StompFrameHandler {
        public int roomId=-1;
        public PageController page;
        public MyStompFrameHandler(PageController page){
            this.page = page;
        }
        @Override
        public Type getPayloadType(StompHeaders headers) {
            return Notification.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            //重要：這邊要自己implement，收到通知之後要做的動作
            // 詳見下方Notification的class
            Notification message = (Notification) payload;
//            System.out.println("Received message: " + message.getContent());
            //TODO: implement notification handling
            if(page.currentFriendName.equals(message.getSenderName())){
                //update friend message box
                chat_messeage_block msg_block = new chat_messeage_block();
                msg_block.add_messeage_block(page.chatroom_messeage_vbox, message.getContent(),false);

            }else if(page.currentRoomId == message.getRoomId()){
                //update room message box
                chat_messeage_block msg_block = new chat_messeage_block();
                msg_block.add_messeage_block(page.chatroom_messeage_vbox, message.getContent(),false);
            }
            Thread scroll = new Thread(()->{
                try {
//				System.out.println("scrolling to buttom");
                    Thread.sleep(50);
                    page.chatroom_scrollpane.setVvalue(1);
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            });
            // 將視窗拉到最下面
            scroll.start();
            //TODO: update room list

        }
    }

    //重要：你會收到一個notificaiton的object，如下：
    public static class Notification {
        private Long roomId;
        private String senderName;
        private String content;
        public Long getRoomId() {
            return roomId;
        }
        public String getSenderName() {
            return senderName;
        }
        public String getContent() {
            return content;
        }

        public Notification() {
            // Default constructor
        }

        public Notification(Long roomId, String senderName, String content) {
            this.roomId = roomId;
            this.senderName = senderName;
            this.content = content;
        }

        //不用管setter，debug 用
        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }
        public void setRoomId(Long roomId) {
            this.roomId = roomId;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }

    //以下不重要
    private static class Auth{
        String id;
        String token;
        public Auth(String id, String token){
            this.id = id;
            this.token = token;
        }
        public Auth() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private static class MyStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("Connected to WebSocket server");
        }
    }


}