package com.connected.model;

import java.util.List;

public interface ChatObserver {

	void onNewChatGroup(List<String> users);

	void onNewMessage(String message, String speakerNick);
	
}
