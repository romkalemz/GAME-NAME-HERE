package time_lapse;

import java.io.*;
import java.net.*;

/*
 * Server accepts client connection and creates new thread for
 * client handler that manages communication. 
 */

public class Server extends Thread {

	@Override
	public void run() {
		System.out.println("Server starting...");
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(5056);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			Socket socket = null;
			try {
				// Holds here until client is found
				socket = server.accept();

				System.out.println("A new client is connected : " + socket);

				System.out.println("Assigning new thread for this client");
				// Creates new thread to run clienHandler
				Thread t = new ClientHandler(socket);
				// start thread with t.start() not with t.run()
				t.start();

			} catch (Exception e) {
				try {
					server.close();
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}
	
	}

}
