package Application;

import Network.FromMaster.WCServer;


public class WCSlaveApp {
	
	static int mFileCounter = 0;

    public static synchronized int IncrementFileCount() {
    	return mFileCounter++;
    }
	
	public static void main(String[] args) {
		WCServer server = new WCServer();
		
		System.out.println("### Slave ###");
		
		server.launch();
	}

}