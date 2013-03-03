package Application;

import Network.FromMaster.PartServer;


public class WCSlaveApp {
	
	static int mFileCounter = 0;

    public static synchronized int IncrementFileCount() {
    	return mFileCounter++;
    }
	
	public static void main(String[] args) {
		PartServer server = new PartServer();
		
		System.out.println("### Slave ###");
		
		server.launch();
	}

}
