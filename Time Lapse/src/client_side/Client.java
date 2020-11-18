package client_side;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public void connect() throws IOException {
		
		try {
			// connect to server
			InetAddress ip = InetAddress.getByName("localhost");
        	Socket socket = new Socket(ip, 5056);
        	
        	Scanner clientIn = new Scanner(System.in);
        	// Set output stream
        	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        	
        	while(true) {
        		//read system.in
        		String sendMessage = clientIn.nextLine();
        		//Write to clientHandler/server
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
