package com.connected.controller;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.connected.model.ChatObserver;
import com.connected.model.Communication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ChatController implements Initializable, ChatObserver {

	@FXML
	private TextField typingField;
	@FXML
	private ImageView arrow;
	@FXML
	private VBox chatArea;
	@FXML
	private ScrollPane scrollChat;
	@FXML
	private VBox listArea;
	@FXML
	private ScrollPane scrollList;
	@FXML
	private Label nickLabel;
	private Image redArrow;
	private Image blackArrow;
	private Image personIcon;
	private String nick;
	private Communication communication;

	public ChatController(String nick, Communication communication) {
		this.nick = nick;
		this.communication = communication;
	}

	public void send(MouseEvent mouseEvent) throws Exception {
		if (!typingField.getText().equals("")) {
			String message = typingField.getText();
			communication.send(nick, message);
			typingField.setText("");
			TextFlow textFlow = new TextFlow();
			Text text = new Text(nick + ": " + message);
			textFlow.getChildren().add(text);
			textFlow.getStyleClass().add("chat-bubble");
			Circle img = new Circle(20, 20, 10);
			img.setFill(new ImagePattern(personIcon));
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.BOTTOM_LEFT);
			hBox.getChildren().addAll(img, textFlow);
			hBox.setSpacing(5);
			chatArea.getChildren().add(hBox);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		scrollChat.vvalueProperty().bind(chatArea.heightProperty());
		chatArea.prefWidthProperty().bind(scrollChat.widthProperty());
		listArea.prefHeightProperty().bind(scrollList.heightProperty());
		nickLabel.setText("Your nick is: " + nick);
		chatArea.setPadding(new Insets(10, 30, 10, 15));
		chatArea.setSpacing(5);
		scrollChat.setPadding(new Insets(5, 5, 5, 8));
		try {
			personIcon = new Image(getClass().getResource("/images/personIcon.png")
					.toURI().toString());
			blackArrow = new Image(getClass().getResource("/images/blackArrow.png")
					.toURI().toString());
			redArrow = new Image(
					getClass().getResource("/images/redArrow.png").toURI().toString());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public void onRelease(MouseEvent mouseEvent) {
		arrow.setImage(blackArrow);
	}

	public void onPress(MouseEvent mouseEvent) {
		arrow.setImage(redArrow);
	}

	public void drawList(List<String> users) {
		Platform.runLater(() -> {
			listArea.getChildren().clear();
			for (int i = 0; i < users.size(); i++) {
				if (!(users.get(i).trim()).equals(nick)) {
					HBox hBox = new HBox();
					hBox.setPrefHeight(40);
					Circle img = new Circle(14);
					img.setFill(new ImagePattern(personIcon));
					Text text = new Text(users.get(i));
					text.setFill(Color.DARKGOLDENROD);
					text.setFont(Font.font("Verdana", 18));
					hBox.getChildren().addAll(img, text);
					Line line = new Line();
					hBox.setSpacing(5);
					hBox.setPadding(new Insets(5, 0, 0, 10));
					line.setStartX(0);
					line.setEndX(205);
					listArea.getChildren().addAll(hBox, line);
				}
			}
		});
	}

	public void drawBubble(String message, String speakerNick) {
		Platform.runLater(() -> {
			TextFlow textFlow = new TextFlow();
			Text text = new Text(speakerNick + ": " + message);
			textFlow.getChildren().add(text);
			textFlow.getStyleClass().add("chat-bubble-receive");
			Circle img = new Circle(20, 20, 10);
			img.setFill(new ImagePattern(personIcon));
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.BOTTOM_RIGHT);
			hBox.setSpacing(5);
			hBox.getChildren().addAll(textFlow, img);
			chatArea.getChildren().add(hBox);
		});
	}

	@Override
	public void onNewChatGroup(List<String> users) {
		drawList(users);
	}

	@Override
	public void onNewMessage(String message, String speakerNick) {
		drawBubble(message, speakerNick);
	}
}
