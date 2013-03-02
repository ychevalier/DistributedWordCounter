package Controllers;

import java.net.Socket;

import Model.Query;
import Network.FromMaster.QueryHandler;

public class MasterHandler implements Runnable {
	
	private Socket mSocket;
	
	public MasterHandler(Socket client) {
		mSocket = client;
	}

	@Override
	public void run() {
		
		System.out.println("Received incoming connection");
		
		QueryHandler qh = new QueryHandler(mSocket);
		Query aQuery = qh.handleQuery();
		
		if(aQuery == null) {
			System.out.println("Part is incorrect, aborting");
			return;
		}
		
		System.out.println("Part is good, processing...");
				
	}
}
