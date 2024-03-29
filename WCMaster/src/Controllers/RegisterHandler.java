package Controllers;

import java.io.IOException;
import java.net.Socket;

import Model.Slave;
import Model.SlaveList;
import Network.FromSlave.RegisterAnalyzer;
import Network.FromSlave.RegisterResponse;

public class RegisterHandler implements Runnable {
	
	private Socket mSocket;
	private SlaveList mSlaveList;
	
	public RegisterHandler(Socket client, SlaveList list) {
		mSocket = client;
		mSlaveList = list;
	}
	
	@Override
	public void run() {
		System.out.println("A new slave wants to register/unregister.");
		
		RegisterAnalyzer ra = new RegisterAnalyzer(mSocket);
		Slave s = ra.handleSlave();
		
		RegisterResponse rr = new RegisterResponse(mSocket);
		boolean response = rr.sendResponse(s);
		
		try {
			mSocket.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		if(!response) {
			System.out.println("Received a incorrect slave, aborting.");
			return;
		}
		
		if(s.isAlive()) {
			mSlaveList.addSlave(s);
			System.out.println("The New Slave has been added!");
		} else {
			mSlaveList.removeSlave(s);
			System.out.println("The Slave has been removed!");
		}

		
	}
	

}
