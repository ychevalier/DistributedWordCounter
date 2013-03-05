package Network.FromSlave;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Controllers.ResultHandler;
import Listeners.PartProcessedListener;

public class ResultServer implements Runnable {
	
	private ServerSocket mServerSocket;
	private int mPort;
	private int mJobId;
	
	private PartProcessedListener mListener;

	public ResultServer(int port, PartProcessedListener listener, int jobId) {
		mPort = port;
		mListener = listener;
		mJobId = jobId;
	}

	@Override
	public void run() {
		try {
			mServerSocket = new ServerSocket(mPort); 
			while (true) {
				Socket client = mServerSocket.accept();
				Thread t = new Thread(new ResultHandler(client, mListener, mJobId));
			    t.start();
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

}
