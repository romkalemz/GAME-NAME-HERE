package time_lapse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9001;
    
    @Override
    public void run() {
        //create the socket server object
    	System.out.println("WE IN");
        try { server = new ServerSocket(port); } 
        catch (IOException e) { e.printStackTrace(); }
        
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            //creating socket and waiting for client connection
            Socket socket = null;
			try { socket = server.accept(); } 
			catch (IOException e) { e.printStackTrace(); }
			
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = null;
			try { ois = new ObjectInputStream(socket.getInputStream()); } 
			catch (IOException e) { e.printStackTrace(); }
			
            //convert ObjectInputStream object to String
            String message = null;
			try { message = (String) ois.readObject(); } 
			catch (ClassNotFoundException | IOException e) { e.printStackTrace(); }
			
            System.out.println("Message Received: " + message);
            //create ObjectOutputStream object
            ObjectOutputStream oos = null;
			try { oos = new ObjectOutputStream(socket.getOutputStream()); } 
			catch (IOException e) { e.printStackTrace(); }
			
            //write object to Socket
            try { oos.writeObject("Hi Client "+message); } 
            catch (IOException e) { e.printStackTrace(); }
            
            //close resources
            try { ois.close(); } 
            catch (IOException e) { e.printStackTrace(); }
            try { oos.close(); } 
            catch (IOException e) { e.printStackTrace(); }
            try { socket.close(); } 
            catch (IOException e) { e.printStackTrace(); }
            
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        try { server.close(); } 
        catch (IOException e) { e.printStackTrace(); }
    }
    
}
