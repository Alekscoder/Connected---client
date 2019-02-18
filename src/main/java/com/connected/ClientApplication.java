package com.connected;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import com.connected.controller.LoginController;
import com.connected.model.Communication;
import com.connected.view.InformationWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClientApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Properties properties = new Properties();
		InputStream input = ClientApplication.class.getResourceAsStream("/configurations/application.properties");
		properties.load(input);
		String serverIp = properties.getProperty("serverIp");
		int serverPort = Integer.parseInt(properties.getProperty("serverPort"));
		Communication communication = new Communication(serverIp, serverPort, Executors.newCachedThreadPool());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
		LoginController loginController = new LoginController(communication,new InformationWindow());
		loader.setController(loginController);
		Parent root = loader.load();
		primaryStage.setTitle("Connected Society");
		Image stageIcon = new Image(
				getClass().getResource("/images/icon.png").toURI().toString());
		primaryStage.getIcons().add(stageIcon);
		primaryStage.setScene(new Scene(root, 530, 310));
		try {
			primaryStage.show();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
