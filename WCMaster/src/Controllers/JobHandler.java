package Controllers;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import Application.WCSMasterApp;
import Model.Query;
import Model.Slave;
import Model.WordList;
import Network.FromClient.QueryAnalyzer;
import Network.FromSlave.ResultServer;
import Network.ToSlave.PartSender;
import System.Utils;

public class JobHandler implements Runnable {

	private Socket mSocket;
	private List<Slave> mWorkingSlaves;
	private WordList mFinalResult;

	public JobHandler(Socket client) {
		mSocket = client;
		mWorkingSlaves = new LinkedList<Slave>();
		mFinalResult = new WordList();
	}

	@Override
	public void run() {

		System.out.println("Handling a new Client");

		QueryAnalyzer qh = new QueryAnalyzer(mSocket);
		Query aQuery = qh.handleQuery();

		if (aQuery == null) {
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
		Thread tR = new Thread(new ResultServer(portForThisJob));
		tR.start();

		System.out.println("Sending a part to a new slave");
		PartSender ps = new PartSender();
		ps.connect("localhost", 8888);
		// ps.checkAvailability();
		ps.sendPart(aQuery.getFilename(), 42, aQuery.getFilePath(),
				"localhost", portForThisJob, 2);
		ps.disconnect();

	}
}
