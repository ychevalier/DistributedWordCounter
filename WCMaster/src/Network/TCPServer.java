package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import System.Config;

public class TCPServer {

	public static void main(String[] args) {
		String clientQuery;
        ServerSocket welcomeSocket;
		try {
			welcomeSocket = new ServerSocket(Config.MASTER_PORT);
			while(true) {
	            Socket connectionSocket = welcomeSocket.accept();
	            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	            do {
	            	clientQuery = inFromClient.readLine();
	            	System.out.println(clientQuery);
	            } while(!clientQuery.contains("end"));
	           
	            
	            outToClient.writeBytes("OK\n");
	            
	            outToClient.flush();
	            outToClient.close();
	            inFromClient.close();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}

        
	}

}
