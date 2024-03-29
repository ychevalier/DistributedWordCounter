package Network.FromClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Controllers.JobHandler;
import System.Config;

public class QueryServer implements Runnable {

	private ServerSocket mServerSocket;
	private int mPort;

	public QueryServer() {
		mPort = Config.MASTER_PORT;
	}

	public QueryServer(int port) {
		mPort = port;
	}

	@Override
	public void run() {
		try {
			System.out.println("Query Server Listening on " + mPort);
			mServerSocket = new ServerSocket(mPort); 
			while (true) {
				Socket client = mServerSocket.accept();
				Thread t = new Thread(new JobHandler(client));
			    t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
