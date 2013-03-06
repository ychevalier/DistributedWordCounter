package Controllers;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Application.WCMasterApp;
import Executers.WordCounter;
import Listeners.PartProcessedListener;
import Model.Query;
import Model.Slave;
import Model.WordList;
import Network.FromClient.QueryAnalyzer;
import Network.FromClient.QueryResponse;
import Network.FromSlave.ResultServer;
import Network.ToClient.ResultSender;
import Network.ToSlave.PartSender;
import System.Config;
import System.Utils;

public class JobHandler implements Runnable, PartProcessedListener {

	private Socket mSocket;
	private Map<Integer, Slave> mWorkingSlaves;
	private WordList mFinalResult;
	private Query mQuery;
	private int mJobId;

	public JobHandler(Socket client) {
		mSocket = client;
		mWorkingSlaves = new TreeMap<Integer, Slave>();
		mFinalResult = new WordList();
		mJobId = WCMasterApp.getFileCount();
	}

	@Override
	public void run() {

		System.out.println("Handling a new Client");

		// Getting & Parsing the query.
		QueryAnalyzer qh = new QueryAnalyzer(mSocket, mJobId);
		mQuery = qh.handleQuery();
		
		QueryResponse qr = new QueryResponse(mSocket);
		boolean response = qr.sendResponse(mQuery, WCMasterApp.amIReady());
		
		try {
			mSocket.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		if (!response) {
			System.out.println("Received a incorrect query, aborting");
			return;
		}

		System.out.println("Received a correct query");

		int portForThisJob;

		do {
			portForThisJob = WCMasterApp.getResultPortCount();
		} while (!Utils.isAvailable(portForThisJob));

		System.out.println("Launching Result Server on port : "
				+ portForThisJob);
		Thread tR = new Thread(new ResultServer(portForThisJob, this, mJobId));
		tR.start();

		List<Slave> availableSlaves = WCMasterApp.mSlaveList.getSlaves();
		PartSender ps = new PartSender();
		for (Slave s : availableSlaves) {
			int part = 42;
			mWorkingSlaves.put(part, s);
			System.out.println("Sending a part to a new slave");
			
			if(!ps.connect(s.getIP(), s.getPort())) {
				WCMasterApp.mSlaveList.removeSlave(s);
			} else {
				ps.sendPart(mQuery.getFilename(), part, mQuery.getFilePath(), Config.MY_IP, portForThisJob, 2);
				ps.disconnect();
			}

		}
	}

	@Override
	public void partProcessed(int part, String filename) {
		mWorkingSlaves.remove(part); 
		
		WordCounter.countToWordList(filename, mFinalResult);
		
		if(mWorkingSlaves.isEmpty()) {
			String finalPath = Config.FINAL_RESULT + mQuery.getFilename() + '_' + mJobId;
			
			System.out.println("Writing File");
			mFinalResult.toFile(finalPath);
			
			ResultSender rs = new ResultSender();
			rs.connect(mQuery.getResultIP(), mQuery.getResultPort());
			rs.sendResult(mQuery.getFilename(), finalPath);

			
			rs.disconnect();
		}
		
	}
}
