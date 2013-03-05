package Controllers;

import java.io.IOException;
import java.net.Socket;

import Listeners.PartProcessedListener;
import Model.ResultFS;
import Network.FromSlave.ResultAnalyzer;
import Network.FromSlave.ResultResponse;

public class ResultHandler implements Runnable {

	private Socket mSocket;
	private PartProcessedListener mListener;
	private int mJobId;
	
	public ResultHandler(Socket client, PartProcessedListener listener, int jobId) {
		mSocket = client;
		mListener = listener;
		mJobId = jobId;
	}
	
	@Override
	public void run() {
		System.out.println("Handling a new Result from Slave");
		
		ResultAnalyzer ra = new ResultAnalyzer(mSocket, mJobId);
		ResultFS aResultFS = ra.handleResult();
		
		ResultResponse rr = new ResultResponse(mSocket);
		boolean response = rr.sendResponse(aResultFS);
		
		try {
			mSocket.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		if(!response) {
			System.out.println("Received a incorrect result, aborting");
			return;
		}
		
		mListener.partProcessed(aResultFS.getPartNumber(), aResultFS.getResultPath());

		System.out.println("Received a correct result");
	}
	
	
}
