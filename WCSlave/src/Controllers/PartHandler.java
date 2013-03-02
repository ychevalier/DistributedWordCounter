package Controllers;

import java.net.Socket;

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
		
		System.out.println("Received incoming connection");
		
		PartAnalyzer qh = new PartAnalyzer(mSocket);
		Part aQuery = qh.handleQuery();
		
		if(aQuery == null) {
			System.out.println("Part is incorrect, aborting");
			return;
		}
		
		System.out.println("Part is good, processing...");
		String outputPath = Config.RESULT_PATH + aQuery.getPartName() + "Part";
		
		// TODO Set a thread which send repeated alive messages to master.
		
		WordCounter wordCount = new WordCounter(aQuery.getPartPath(), outputPath);
	    wordCount.count();
		
		System.out.println("Sending a result to master at " + aQuery.getResultIP() + ":" + aQuery.getResultPort());
		ResultSender ps = new ResultSender();
		ps.connect(aQuery.getResultIP(), aQuery.getResultPort());
		ps.sendResult(aQuery.getPartName(), outputPath);
		ps.disconnect();
	}
}