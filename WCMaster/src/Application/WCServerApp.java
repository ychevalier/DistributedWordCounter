package Application;

import Network.FromClient.QueryServer;


public class WCServerApp {
	
	static int mFileCounter = 0;

    public static synchronized int IncrementFileCount() {
    	return mFileCounter++;
    }
	
	public static void main(String[] args) {
		
		System.out.println("### Master ###");
		QueryServer server = new QueryServer();
		server.launch();
	}

}
