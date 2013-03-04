package Controllers;

import java.io.IOException;
import java.net.Socket;

import Application.WCSlaveApp;
import Executers.WordCounter;
import Model.Part;
import Network.FromMaster.PartAnalyzer;
import Network.ToMaster.ResultSender;
import System.Config;

public class PartHandler implements Runnable {
	
	private Socket mSocket;
	
	public PartHandler(Socket client) {
		mSocket = client;
	}

	@Override
	public void run() {
		
		System.out.println("Receiving a New Part");
		
		PartAnalyzer qh = new PartAnalyzer(mSocket);
		Part aQuery = qh.handleQuery();
		
		if(aQuery == null) {
			System.out.println("Error : Part is incorrect, Aborting");
			return;
		}

		String outputPath = Config.RESULT_PATH + aQuery.getFileName() + aQuery.getPartNumber() + '_' + WCSlaveApp.getNextFileCount();
		
		// TODO Set a thread which send repeated alive messages to master.
		
		WordCounter wordCount = new WordCounter(aQuery.getPartPath(), outputPath);
		try {
			wordCount.count();
		} catch(IOException e) {
			System.out.println("Error : Could not write in output file, Aborting");
			return;
		}
		
		System.out.println("Sending Result to Master at " + aQuery.getResultIP() + ':' + aQuery.getResultPort());
		
		ResultSender ps = new ResultSender();
		ps.connect(aQuery.getResultIP(), aQuery.getResultPort());
		ps.sendResult(aQuery.getFileName(), aQuery.getPartNumber(), outputPath);
		ps.disconnect();
	}
}