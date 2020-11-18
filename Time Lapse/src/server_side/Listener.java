package server_side;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/*
 * This is where the information is recieved from the client and sent over to the client.
 * One listener per player allowing for multiple connections. (future potential)
 */

public class Listener extends Thread {

	Socket socket;
	ObjectInputStream ois;
	
	public Listener(Socket client) throws IOException {
		this.socket = client;
		this.ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		String received;
		while(true) {
			try {
				// read input from the player (from the client side)
				received = (String) ois.readObject();
				System.out.println("Server: received from client: " + received);
				if(received.equalsIgnoreCase("exit")) {
					System.out.println("Server: closing connection with client");
					break;
				}
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		// close if the client is disconnecting
		try {
			socket.close();
    		ois.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
	}
	
	

}
