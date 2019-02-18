package com.connected.controller;

import java.net.Socket;
import java.util.concurrent.Executor;
import com.connected.model.Communication;
import com.connected.model.Interpreter;
import com.connected.model.LinkedNode;
import com.connected.view.InformationWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField nickField;
	@FXML
	private AnchorPane loginPane;
	@FXML
	private PasswordField passwordField;
	private Communication communication;
	private InformationWindow information;

	public LoginController(Communication communication, InformationWindow information) {
		this.communication = communication;
		this.information = information;
	}

    public void login(MouseEvent mouseEvent) throws Exception {
		if (!nickField.getText().equals("") && !passwordField.getText().equals("")) {
			Stage stage = (Stage) loginPane.getScene().getWindow();
			String nick = nickField.getText();
			String password = passwordField.getText();
			communication.logIn(nick, password);
			if (!communication.logInResponse()) {
				information.displayInformation("Login failed. Please try again.", "ALERT");
			} else {
				displayChat(communication.getSocket(), communication.getExecutor(), nick);
				stage.close();
			}
		} else
			information.displayInformation("Please, fill blank fields.", "ALERT");
	}

	public void displayChat(Socket socket, Executor executor, String nick) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chat.fxml"));
		ChatController chatController = new ChatController(nick, communication);
		loader.setController(chatController);
		Parent root = loader.load();
		Stage chatStage = new Stage();
		chatStage.setScene(new Scene(root, 600, 400));
		Image stageIcon = new Image(
				getClass().getResource("/images/icon.png").toURI().toString());
		chatStage.getIcons().add(stageIcon);
		chatStage.setTitle("Connected");
		chatStage.show();
		chatStage.setOnCloseRequest(e -> {
			communication.exit(nick);
			System.exit(0);
		});
		executor.execute(new LinkedNode(socket, new Interpreter(chatController)));
	}

	public void displayAccount(MouseEvent mouseEvent) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Account.fxml"));
		AccountController accountController = new AccountController(communication, information);
		loader.setController(accountController);
		Parent root = loader.load();
		Stage stage = new Stage();
		Image stageIcon = new Image(
				getClass().getResource("/images/icon.png").toURI().toString());
		stage.getIcons().add(stageIcon);
		stage.setTitle("Connected - Account");
		stage.setScene(new Scene(root, 610, 340));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
}