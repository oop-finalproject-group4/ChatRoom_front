module oopfinalproject.ChatRoomMainPage06181148 {
    requires javafx.controls;
    requires javafx.fxml;
//  requires maven.compiler.plugin;
//    requires tyrus.standalone.client;
    requires spring.boot.starter.websocket;
    requires spring.messaging;
//    requires spring.websocket;
    requires org.json;
    opens oopfinalproject.ChatRoomMainPage06181148 to javafx.fxml,javafx.controls;
    exports oopfinalproject.ChatRoomMainPage06181148;
}
