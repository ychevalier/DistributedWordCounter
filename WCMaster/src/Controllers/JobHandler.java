package Controllers;

import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import Application.WCSMasterApp;
import Executers.WordCounter;
import Listeners.PartProcessedListener;
import Model.Query;
import Model.Slave;
import Model.WordList;
import Network.FromClient.QueryAnalyzer;
import Network.FromSlave.ResultServer;
import Network.ToClient.ResultSender;
import Network.ToSlave.PartSender;
import System.Utils;

public class JobHandler implements Runnable, PartProcessedListener {

	private Socket mSocket;
	private Map<Integer, Slave> mWorkingSlaves;
	private WordList mFinalResult;
	private Query mQuery;

	public JobHandler(Socket client) {
		mSocket = client;
		mWorkingSlaves = new TreeMap<Integer, Slave>();
		mFinalResult = new WordList();
	}

	@Override
	public void run() {

		System.out.println("Handling a new Client");

		// Getting & Parsing the query.
		QueryAnalyzer qh = new QueryAnalyzer(mSocket);
		mQuery = qh.handleQuery();

		if (mQuery == null) {
			System.out.println("Received a incorrect query, aborting");
			return;
		}

		System.out.println("Received a correct query");

		int portForThisJob;

		do {
			portForThisJob = WCSMasterApp.getResultPortCount();
		} while (!Utils.isAvailable(portForThisJob));

		System.out.println("Launching Result Server on port : "
				+ portForThisJob);
		Thread tR = new Thread(new ResultServer(portForThisJob, this));
		tR.start();

		Slave s = new Slave("localhost", 8888);
		int part = 42;
		mWorkingSlaves.put(part, s);
		System.out.println("Sending a part to a new slave");
		PartSender ps = new PartSender();
		ps.connect(s.getIP(), s.getPort());
		// ps.checkAvailability();
		ps.sendPart(mQuery.getFilename(), part, mQuery.getFilePath(),
				"localhost", portForThisJob, 2);
		ps.disconnect();
		
		Slave s2 = new Slave("localhost", 8889);
		part = 43;
		mWorkingSlaves.put(part, s2);
		System.out.println("Sending a part to a new slave");
		//PartSender ps2 = new PartSender();
		ps.connect(s2.getIP(), s2.getPort());
		// ps.checkAvailability();
		ps.sendPart(mQuery.getFilename(), part, mQuery.getFilePath(),
				"localhost", portForThisJob, 2);
		ps.disconnect();

	}

	@Override
	public void partProcessed(int part, String filename) {
		mWorkingSlaves.remove(part); // Or Integer??
		
		WordCounter.countToWordList(filename, mFinalResult);
		
		if(mWorkingSlaves.isEmpty()) {
			System.out.println("Sending result to client");
			ResultSender rs = new ResultSender();
			rs.connect(mQuery.getResultIP(), mQuery.getResultPort());
			rs.sendResult(mQuery.getFilename(), mFinalResult);
			rs.disconnect();
		}
		
	}
}
