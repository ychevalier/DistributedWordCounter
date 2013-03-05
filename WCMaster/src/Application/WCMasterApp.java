package Application;

import java.net.InetAddress;
import java.net.UnknownHostException;

import Network.FromSlave.RegisterServer;
import Model.SlaveList;
import Network.FromClient.QueryServer;
import System.Config;
import System.Utils;

public class WCMasterApp {

	public static SlaveList mSlaveList = new SlaveList();

	static int mFileCounter = 0;

	public static synchronized int getFileCount() {
		return mFileCounter++;
	}

	static int mResultPortCounter = 0;

	public static synchronized int getResultPortCount() {

		return Config.RESULT_MIN_PORT_NUMBER
				+ (mResultPortCounter++ % (Config.RESULT_MAX_PORT_NUMBER - Config.RESULT_MIN_PORT_NUMBER));
	}

	public static boolean amIReady() {
		return !mSlaveList.isEmpty();
	}

	public static void main(String[] args) {

		System.out.println("### Master ###");

		try {
			Config.MY_IP = InetAddress.getLocalHost().toString().split("/")[1];
			if (!Utils.checkIP(Config.MY_IP)) {
				System.out.println("Unable to find host address");
				return;
			}
		} catch (UnknownHostException e) {
			// e.printStackTrace();
			System.out.println("Unable to find host address");
			return;
		}

		Thread tQ = new Thread(new QueryServer());
		tQ.start();

		Thread tR = new Thread(new RegisterServer(mSlaveList));
		tR.start();

	}

}
