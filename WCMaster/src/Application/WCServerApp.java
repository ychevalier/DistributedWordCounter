package Application;

import Network.WCServer;


public class WCServerApp {
	
	static int mFileCounter = 0;

    public static synchronized int IncrementFileCount() {
    	return mFileCounter++;
    }
	
	public static void main(String[] args) {
		WCServer server = new WCServer();
		
		server.launch();
	}

}