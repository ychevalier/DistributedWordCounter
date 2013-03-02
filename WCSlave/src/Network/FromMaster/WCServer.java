package Network.FromMaster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Controllers.MasterHandler;
import System.Config;

public class WCServer {

	private ServerSocket mServerSocket;
	private int mPort;

	public WCServer() {
		mPort = Config.SLAVE_PORT;
	}

	public WCServer(int port) {
		mPort = port;
	}

	public void launch() {
		try {
			mServerSocket = new ServerSocket(mPort); 
			while (true) {
				Socket client = mServerSocket.accept();
				Thread t = new Thread(new MasterHandler(client));
			    t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}