package Controllers;

import java.net.Socket;

import Model.ResultFS;
import Network.FromSlave.ResultAnalyzer;

public class ResultHandler implements Runnable {

	private Socket mSocket;
	
	public ResultHandler(Socket client) {
		mSocket = client;
	}
	
	@Override
	public void run() {
		System.out.println("Handling a new Result from Slave");
		
		ResultAnalyzer ra = new ResultAnalyzer(mSocket);
		ResultFS aResultFS = ra.handleResult();
		
		if(aResultFS == null) {
			System.out.println("Received a incorrect result, aborting");
			return;
		}
		
		System.out.println("Received a correct result");
	}
	
	
}
