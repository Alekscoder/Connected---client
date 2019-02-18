module ConnectedChatClient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    exports com.connected;
    opens com.connected.controller;
}