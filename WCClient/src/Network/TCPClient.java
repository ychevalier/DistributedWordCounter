package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import System.Config;

public class TCPClient {
	
	private static final int MAX_OUTPUT_BUFFER = 2048;
	
	private Socket mSocket;

	boolean connect() {	
		try {
			if(mSocket == null) {
				mSocket = new Socket(Config.MASTER_IP, Config.MASTER_PORT);
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	void disconnect() {
		if(mSocket != null) {
			try {
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	String sendData(String toSend) {
		String response = null;
		
		try {
			DataOutputStream outToServer = new DataOutputStream(mSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			
			outToServer.writeBytes(toSend);
	        response = inFromServer.readLine();
	        
	        outToServer.flush();
	        outToServer.close();
	        inFromServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return response;
	}
	
	String sendFile(String header, File file, String footer) {
		
		String response = null;

		try {
			OutputStream output = mSocket.getOutputStream();     
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		    FileInputStream fileInputStream = new FileInputStream(file);
		    
		    byte[] buffer = new byte[MAX_OUTPUT_BUFFER];
		    int bytesRead = 0;
		    
		    output.write(header.getBytes());

		    while((bytesRead = fileInputStream.read(buffer))>0) {
		        output.write(buffer,0,bytesRead);
		    }
		    
		    output.write(footer.getBytes());
		    
		    response = inFromServer.readLine();
		    
		    output.flush();
		    output.close();
		    inFromServer.close();
		    fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;

	}

}