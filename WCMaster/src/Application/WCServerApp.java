package Application;

import Network.FromClient.QueryServer;


public class WCServerApp {
	
	static int mFileCounter = 0;

    public static synchronized int IncrementFileCount() {
    	return mFileCounter++;
    }
	
	public static void main(String[] args) {
		
		System.out.println("### Master ###");
		
		System.out.println("Launching Query Server");
		Thread tQ = new Thread(new QueryServer());
	    tQ.start();
	    
	}

}
