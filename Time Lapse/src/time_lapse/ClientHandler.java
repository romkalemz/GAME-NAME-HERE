package time_lapse;

import java.io.*;
import java.net.*;

/*
 * ClientHandler manages communication between server and client,
 * it reads input from client.
 */

public class ClientHandler extends Thread {
		final ObjectInputStream ois;
	    final Socket socket;
	    
	    public ClientHandler(Socket socket) throws IOException {
	    	this.socket = socket;
	    	//Set input stream
	    	this.ois = new ObjectInputStream(socket.getInputStream());
	    }
	    
	    @Override
		public void run() {
	    	String received;
	    	while(true) {
	    		try {
	    			received = (String) ois.readObject(); 
					System.out.println("Server: received from client: " + received);
					if(received.equalsIgnoreCase("exit")) {
						System.out.println("Server: closing connection with client");
						break;
					}
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 		
	    	}
	    	try {
	    		ois.close();
	    	} catch(IOException e) {
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
}