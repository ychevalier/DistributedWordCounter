package Network.FromServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Controllers.ResultHandler;

public class ResultServer implements Runnable {
	
	private ServerSocket mServerSocket;
	private int mPort;

	public ResultServer(int port) {
		mPort = port;
	}

	@Override
	public void run() {
		try {
			mServerSocket = new ServerSocket(mPort); 
			while (true) {
				Socket client = mServerSocket.accept();
				Thread t = new Thread(new ResultHandler(client));
			    t.start();
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

}
