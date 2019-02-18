package com.connected.view;

import com.connected.controller.InformationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InformationWindow {
	public void displayInformation(String alert,String header) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Information.fxml"));
		InformationController informationController = new InformationController();
		fxmlLoader.setController(informationController);
		Parent root = fxmlLoader.load();
		Stage alertStage = new Stage();
		informationController.getHeader().setText(header);
		informationController.getInformation().setText(alert);
		alertStage.getIcons().add(new Image(
				getClass().getResource("/images/icon.png").toURI().toString()));
		alertStage.setScene(new Scene(root, 400, 230));
		alertStage.initModality(Modality.APPLICATION_MODAL);
		alertStage.initStyle(StageStyle.UNDECORATED);
		alertStage.showAndWait();
	}
}
