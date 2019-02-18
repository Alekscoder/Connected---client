package com.connected.model;

import java.net.Socket;
import java.util.Scanner;

public class LinkedNode implements Runnable {

	private Socket socket;
	private Interpreter interpreter;

	public LinkedNode(Socket socket, Interpreter interpreter) throws Exception {
		this.socket = socket;
		this.interpreter =interpreter; 
	}

	@Override
	public void run() {
		try (Scanner scanner = new Scanner(socket.getInputStream())) {
			while (true) {
				if (scanner.hasNextLine()) {
					String serverMessage = scanner.nextLine(); 
					String action = interpreter.extractAction(serverMessage);
					int order = interpreter.getOrder(action);
					interpreter.followTheOrder(order, serverMessage);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}