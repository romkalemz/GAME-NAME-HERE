package time_lapse;

import java.io.*;
import java.util.*;
import java.net.*;
/*
 * Client takes input from system.in and writes to ClientHandler
 */
public class Client extends Thread {    
    private void runClient() throws IOException{
    	try {
    		//connect to server
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
    
    @Override
    public void run() { 
    	try {
			runClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
