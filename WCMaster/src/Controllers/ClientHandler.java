package Controllers;

import java.net.Socket;

import Model.Query;
import Network.QueryHandler;

public class ClientHandler implements Runnable {
	
	private Socket mSocket;
	
	public ClientHandler(Socket client) {
		mSocket = client;
	}

	@Override
	public void run() {
		
		QueryHandler qh = new QueryHandler(mSocket);
		Query aQuery = qh.handleQuery();
		
		if(aQuery != null) {
			// Yeahhh
		}
				
	}
}
