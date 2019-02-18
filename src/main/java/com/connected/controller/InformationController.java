package com.connected.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InformationController {

	@FXML
	private Button confirmation;
	@FXML
	private Label information;
	@FXML
	private Label header;

	public Label getInformation() {
		return information;
	}

	public Label getHeader() {
		return header;
	}

	public void close(MouseEvent mouseEvent) {
		Stage stage = (Stage) confirmation.getScene().getWindow();
		stage.close();
	}
}
