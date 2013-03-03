package Application;


import Network.FromClient.QueryServer;
import System.Config;

public class WCSMasterApp {

	static int mFileCounter = 0;

	public static synchronized int getFileCount() {
		return mFileCounter++;
	}

	static int mResultPortCounter = 0;

	public static synchronized int getResultPortCount() {

		return Config.RESULT_MIN_PORT_NUMBER
				+ (mResultPortCounter++ % (Config.RESULT_MAX_PORT_NUMBER - Config.RESULT_MIN_PORT_NUMBER));
	}

	public static void main(String[] args) {

		System.out.println("### Master ###");

		System.out.println("Launching Query Server");
		Thread tQ = new Thread(new QueryServer());
		tQ.start();

	}

}
