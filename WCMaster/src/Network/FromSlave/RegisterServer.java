package Network.FromSlave;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Controllers.RegisterHandler;
import Model.SlaveList;
import System.Config;

public class RegisterServer implements Runnable {
	
	private ServerSocket mServerSocket;
	private int mPort;
	private SlaveList mSlaveList;

	public RegisterServer(SlaveList list) {
		mPort = Config.MASTER_REGISTER_PORT;
		mSlaveList = list;
	}

	@Override
	public void run() {
		try {
			mServerSocket = new ServerSocket(mPort); 
			System.out.println("Register Server Listening on " + mPort);
			while (true) {		
				Socket client = mServerSocket.accept();
				Thread t = new Thread(new RegisterHandler(client, mSlaveList));
			    t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}