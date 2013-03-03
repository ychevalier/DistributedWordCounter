package Application;
import Network.FromServer.ResultServer;
import Network.ToMaster.WCQueryClient;
import System.Config;
import System.Utils;


public class WCClientApp {

	static int mResultPortCounter = 0;

	public static synchronized int getResultPortCount() {

		return Config.RESULT_MIN_PORT_NUMBER
				+ (mResultPortCounter++ % (Config.RESULT_MAX_PORT_NUMBER - Config.RESULT_MIN_PORT_NUMBER));
	}

	public static void main(String[] args) {
		WCQueryClient client = new WCQueryClient();
		
		System.out.println("Starting Client");
		
		if(client.connect()) {
			System.out.println("Client is Connected to Master");
		} else {
			System.out.println("Unable to Connect");
		}
		
		/*
		if(client.checkAvailability()) {
			System.out.println("Master is Available");
			
		} else {
			System.out.println("Master is not Available");
		}
		*/
		
		int portForThisJob;

		do {
			portForThisJob = getResultPortCount();
		} while (!Utils.isAvailable(portForThisJob));

		System.out.println("Launching Result Server on port : "
				+ portForThisJob);
		Thread tR = new Thread(new ResultServer(portForThisJob));
		tR.start();
		
		if(client.sendFile("truc", "machin.txt", "192.168.1.1", 1)) {
			System.out.println("File Sent with Success");
			
		} else {
			System.out.println("File not sent");
		}
		
		client.disconnect();
		
		System.out.println("Closing Client");
		
	}

}
