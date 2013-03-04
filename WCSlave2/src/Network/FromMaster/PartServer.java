package Network.FromMaster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Controllers.PartHandler;
import System.Config;

public class PartServer {

	private ServerSocket mServerSocket;
	private int mPort;

	public PartServer() {
		mPort = Config.SLAVE_PORT + 1;
	}

	public PartServer(int port) {
		mPort = port;
	}

	public void launch() {
		try {
			
			mServerSocket = new ServerSocket(mPort); 
			System.out.println("Launching Slave Server on port " + mPort);
			while (true) {
				Socket client = mServerSocket.accept();
				Thread t = new Thread(new PartHandler(client));
			    t.start();
			}
		} catch (IOException e) {
			System.out.println("Error : Stopping Slave Server");
			//e.printStackTrace();
		}
	}


}
