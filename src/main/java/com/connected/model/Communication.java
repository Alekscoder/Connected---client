package com.connected.model;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;

public class Communication {

	private Socket socket;
	private PrintWriter printWriter;
	private Scanner scanner;
	private Executor executor;

	public Communication(String host, int port, Executor executor) {
		this.executor = executor;
		try {
			socket = new Socket(host, port);
			this.scanner = new Scanner(new InputStreamReader(socket.getInputStream()));
			this.printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void send(String nick, String message) {
		printWriter.println("sendMessage" + "#&#" + nick + "#&#" + message);
		printWriter.flush();
	}

	public void logIn(String nick, String password) {
		printWriter.println("logIn" + "#&#" + nick + "#&#" + password);
		printWriter.flush();
	}

	public void createAccount(String nick, String password) {
		printWriter.println("createAccount" + "#&#" + nick + "#&#" + password);
		printWriter.flush();
	}

	public void exit(String nick) {
		printWriter.println("exit" + "#&#" + nick);
		printWriter.flush();
	}

	public boolean accountResponse() throws Exception {
		boolean account = false;
		if (scanner.hasNextLine()) {
			String requestCatch = scanner.nextLine();
			if (requestCatch.equals("true")) {
				System.out.println("Creation completed");
				account = true;
			}
		}
		return account;
	}

	public boolean logInResponse() throws Exception {
		boolean login = false;
		if (scanner.hasNextLine()) {
			String requestCatch = scanner.nextLine();
			if (requestCatch.equals("true")) {
				login = true;
			}
		}
		return login;
	}
}
