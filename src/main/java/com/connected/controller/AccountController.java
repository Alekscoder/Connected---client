package com.connected.controller;

import com.connected.model.Communication;
import com.connected.view.InformationWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountController {

	@FXML
	private Button account;
	@FXML
	private TextField newNick;
	@FXML
	private TextField newPassword;
	@FXML
	private AnchorPane accountPane;
	@FXML
	private VBox alertPane;
	private Communication communication;
	private InformationWindow information;

	public AccountController(Communication communication, InformationWindow information) {
		this.communication = communication;
		this.information = information;
	}

	public void create(MouseEvent mouseEvent) throws Exception {
		if (!newNick.getText().equals("") && !newPassword.getText().equals("")) {
			if (newNick.getText().length() > 2 && newPassword.getText().length() > 4) {
				String nick = newNick.getText();
				String password = newPassword.getText();
				Stage stage = (Stage) accountPane.getScene().getWindow();
				communication.createAccount(nick, password);
				if (!communication.accountResponse()) {
					information.displayInformation("User with that nick already exist.", "ALERT");
				} else {
					information.displayInformation("Your account has been created successfully.", "GOOD JOB");
					stage.close();
				}
			} else
				information.displayInformation(
						"Nick need to be minimum 3 characters long \n and password 5 characters long.", "ALERT");
		} else
			information.displayInformation("Please, fill blank fields.", "ALERT");
	}
}
