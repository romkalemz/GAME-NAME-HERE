package client_side;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public void connect() throws IOException {
		
		
		// connect to server
		// loop until the client connects to the server
		Socket socket = attempt_to_connect();
		
		// connection successful!
		// thread to read KB input and send to server
		readKeyboard(socket);
		
		// read input from server, and render game
		read_and_render(socket);
		
	}
	
	private void read_and_render(Socket server) {
		
	}
	
	private Socket attempt_to_connect() throws IOException {
		boolean connected = false;	//whether the client connected to server or not
		Socket socket = null;
		
		while(!connected) {
			try {
				InetAddress ip = InetAddress.getByName("localhost");
				socket = new Socket(ip, 5056);
				connected = true;
				System.out.println("Connection Successful: " + socket);				
				break;
				
			} catch (ConnectException e) {
				System.out.println("Failed to connect to server. Attempting in 3 seconds...");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}	
		return socket;
	}
	
	private void readKeyboard(Socket socket) {
		Thread readKB = new Thread() {
			public void run() {
				try {
					Scanner clientIn = new Scanner(System.in);
					// Set output stream
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		        	
					while(true) {
						//read system.in
						String sendMessage = clientIn.nextLine();
						oos.writeObject(sendMessage);
						if(sendMessage.equalsIgnoreCase("exit")) {
							System.out.println("Client: client is closing");
							break;
						}
					}
		        	
		        	oos.close();
		        	socket.close();
		        	clientIn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		readKB.start();
	}


}
