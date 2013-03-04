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
		
		if(client.connect(Config.MASTER_IP, Config.MASTER_PORT)) {
			System.out.println("Client is Connected to Master " + Config.MASTER_IP + ':' + Config.MASTER_PORT);
		} else {
			System.out.println("Unable to Connect");
			return;
		}
		
		int portForThisJob;

		do {
			portForThisJob = getResultPortCount();
		} while (!Utils.isAvailable(portForThisJob));

		System.out.println("Launching Result Server on port : "
				+ portForThisJob);
		Thread tR = new Thread(new ResultServer(portForThisJob));
		tR.start();
		
		if(client.sendFile("truc", "machin.txt", "localhost", portForThisJob)) {
			System.out.println("File Sent with Success");
			
		} else {
			System.out.println("File not sent");
		}
		
		client.disconnect();
		
	}

}
