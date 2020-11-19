package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * This class opens up the server socket for players to connect to.
 * Once a player connects, create a listener that reads and writes to the connected client
 */


public class Server {
	
	private static ServerSocket server;

	public static void main(String[] args) throws IOException {
		
		System.out.println("Server starting...");
		
		server = new ServerSocket(5056);
		
		// listen for connections to be made to server
		while(true) {
			
			Socket client = server.accept();
				
			System.out.println("A new client is connected : " + client);
			// create a listener for that specific client (multiplayer possiblies)
			Thread listener = new Listener(client);
			listener.start();
		}
	}

}
