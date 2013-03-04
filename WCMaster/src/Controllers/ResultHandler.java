package Controllers;

import java.net.Socket;

import Listeners.PartProcessedListener;
import Model.ResultFS;
import Network.FromSlave.ResultAnalyzer;

public class ResultHandler implements Runnable {

	private Socket mSocket;
	private PartProcessedListener mListener;
	
	public ResultHandler(Socket client, PartProcessedListener listener) {
		mSocket = client;
		mListener = listener;
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
		
		mListener.partProcessed(aResultFS.getPartNumber(), aResultFS.getResultPath());

		System.out.println("Received a correct result");
	}
	
	
}
