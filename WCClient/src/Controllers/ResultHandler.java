package Controllers;

import java.io.IOException;
import java.net.Socket;

import Model.ResultFM;
import Network.FromServer.ResultAnalyzer;
import Network.FromServer.ResultResponse;

public class ResultHandler implements Runnable {

	private Socket mSocket;
	
	public ResultHandler(Socket client) {
		mSocket = client;
	}
	
	@Override
	public void run() {
		System.out.println("Handling a new Result from Master");
		
		ResultAnalyzer ra = new ResultAnalyzer(mSocket);
		ResultFM aResultFM = ra.handleResult();
		
		ResultResponse qr = new ResultResponse(mSocket);
		boolean response = qr.sendResponse(aResultFM);
		
		try {
			mSocket.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		if (!response) {
			System.out.println("Received a incorrect result, aborting");
			return;
		}
		
		System.out.println("Received a correct result");
	}
	
	
}
