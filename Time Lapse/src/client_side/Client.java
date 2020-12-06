package client_side;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	
	
	
	public void connect() throws IOException {
		
		boolean connected = false;	//whether the client connected to server or not
		Socket socket = null;
		
		// connect to server
		// loop until the client connects to the server
		while(!connected) {
			try {
				InetAddress ip = InetAddress.getByName("localhost");
				socket = new Socket(ip, 5056);
				connected = true;
				System.out.println("Connection Successful: " + socket);
				
			} catch (ConnectException e) {
				System.out.println("Failed to connect to server. Attempting in 3 seconds...");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		// connection successful, now read keyboard input and render game
		try {
			//TODO: STILL IN PROGRESS, ONLY READS TERMINAL INPUT 
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

}
