package com.connected.model;

import java.util.Arrays;
import java.util.List;

public class Interpreter {

	private ChatObserver chatObserver;

	public Interpreter(ChatObserver chatObserver) {
		this.chatObserver = chatObserver;
	}

	public List<String> extractList(String serverMessage) {
		String[] caughtMessage = serverMessage.split("#&#");
		String split = caughtMessage[0].replace("[", "").replace("]", "");
		return Arrays.asList(split.split(","));
	}

	public String extractAction(String serverMessage) {
		String[] caughtMessage = serverMessage.split("#&#");
		return caughtMessage[1];
	}

	public String extractSpeakerNick(String serverMessage) {
		String[] caughtMessage = serverMessage.split("#&#");
		return caughtMessage[2];
	}

	public String extractMessage(String serverMessage) {
		String[] caughtMessage = serverMessage.split("#&#");
		return caughtMessage[3];
	}

	public int getOrder(String action) {
		if (action.equals("sendMessage"))
			return 1;
		else if (action.equals("sendOnlineUsers"))
			return 2;
		else
			return 3;
	}

	public void followTheOrder(int order, String serverMessage) throws Exception {
		switch (order) {
		case 1: {
			String message = extractMessage(serverMessage);
			String speakerNick = extractSpeakerNick(serverMessage);
			chatObserver.onNewMessage(message, speakerNick);
			break;
		}
		case 2: {
			List<String> users = extractList(serverMessage);
			chatObserver.onNewChatGroup(users);
			break;
		}
		default:
			break;
		}
	}
}
