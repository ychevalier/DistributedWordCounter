package Controllers;

import java.net.Socket;

import Model.Query;
import Network.FromClient.QueryHandler;
import Network.ToSlave.PartSender;

public class JobHandler implements Runnable {
	
	private Socket mSocket;
	
	public JobHandler(Socket client) {
		mSocket = client;
	}

	@Override
	public void run() {
		
		System.out.println("Handling a new Client");
		
		QueryHandler qh = new QueryHandler(mSocket);
		Query aQuery = qh.handleQuery();
		
		if(aQuery == null) {
			System.out.println("Received a incorrect query, aborting");
			return;
		}
		
		System.out.println("Received a correct query");
		
		System.out.println("Sending a part to a new slave");
		PartSender ps = new PartSender();
		ps.connect("localhost", 8123);
		//ps.checkAvailability();
		ps.sendFile(aQuery.getFilename(), aQuery.getFilePath(), "localhost", 1235, 1234);
		ps.disconnect();
	}
}