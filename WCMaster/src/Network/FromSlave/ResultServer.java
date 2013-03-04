package Network.FromSlave;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Controllers.ResultHandler;
import Listeners.PartProcessedListener;

public class ResultServer implements Runnable {
	
	private ServerSocket mServerSocket;
	private int mPort;
	
	private PartProcessedListener mListener;

	public ResultServer(int port, PartProcessedListener listener) {
		mPort = port;
		mListener = listener;
	}

	@Override
	public void run() {
		try {
			mServerSocket = new ServerSocket(mPort); 
			while (true) {
				Socket client = mServerSocket.accept();
				Thread t = new Thread(new ResultHandler(client, mListener));
			    t.start();
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

}
