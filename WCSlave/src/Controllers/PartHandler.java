package Controllers;

import java.io.IOException;
import java.net.Socket;

import Application.WCSlaveApp;
import Executers.WordCounter;
import Model.Part;
import Network.FromMaster.PartAnalyzer;
import Network.FromMaster.PartResponse;
import Network.ToMaster.ResultSender;
import System.Config;

public class PartHandler implements Runnable {
	
	private Socket mSocket;
	private int mJobId;
	
	public PartHandler(Socket client) {
		mSocket = client;
		mJobId = WCSlaveApp.getNextFileCount();
	}

	@Override
	public void run() {
		
		System.out.println("Receiving a New Part");
		
		PartAnalyzer pa = new PartAnalyzer(mSocket, mJobId);
		Part aPart = pa.handlePart();
		
		PartResponse pr = new PartResponse(mSocket);
		boolean response = pr.sendResponse(aPart, WCSlaveApp.amIReady());
		
		try {
			mSocket.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		if (!response) {
			System.out.println("Error : Part is incorrect, Aborting");
			return;
		}

		String outputPath = Config.RESULT_PATH + aPart.getFileName() + '_' + aPart.getPartNumber() + '_' + mJobId;
		
		// TODO Set a thread which send repeated alive messages to master.
		
		WordCounter wordCount = new WordCounter(aPart.getPartPath(), outputPath);
		try {
			wordCount.count();
		} catch(IOException e) {
			System.out.println("Error : Could not write in output file, Aborting");
			return;
		}
		
		System.out.println("Sending Result to Master at " + aPart.getResultIP() + ':' + aPart.getResultPort());
		
		ResultSender ps = new ResultSender();
		ps.connect(aPart.getResultIP(), aPart.getResultPort());
		ps.sendResult(aPart.getFileName(), aPart.getPartNumber(), outputPath);
		ps.disconnect();
	}
}
