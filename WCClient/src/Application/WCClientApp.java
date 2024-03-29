package Application;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
    
    static boolean simpleCliParser(String [] args) {
    	
    	boolean isError = false;
    	
    	if(args.length != 8) {
    		isError = true;
    	} else {	
	    	for(int x = 0; x < args.length; x++){
	    	    if("-f".equals(args[x])){
	    	    	Config.FILE_PATH = args[x + 1];
	    	    	File f = new File(Config.FILE_PATH);
	    	    	if(!f.exists()) {
	    	    		System.out.println("File Path is Incorrect.");
	    	        	isError = true;
	    	        	break;
	    	    	}
	    	        x++;
	    	    } else if("-j".equals(args[x])){
	    	        Config.JOB_NAME = args[x + 1];       
	    	        x++;
	    	    } else if("-ma".equals(args[x])){
	    	        Config.MASTER_IP = args[x + 1];
	    	        if(!Utils.checkIP(Config.MASTER_IP)) {
	    	        	System.out.println("Remote master IP is incorrect.");
	    	        	isError = true;
	    	        	break;
	    	        }	        
	    	        x++;
	    	    } else if("-mp".equals(args[x])){
	    	    	try {
	    	            Config.MASTER_PORT = Integer.parseInt(args[x + 1]);
	    	        } catch (NumberFormatException e) {
	    	            System.out.println("Remote master port must be an integer.");
	    	            isError = true;
	    	            break;
	    	        }
	    	        x++;
	    	    } else {
	    	    	isError = true;
	    	    	System.out.println("Wrong argument.");
	    	    	break;
	    	    }
	    	}
    	}

    	if(isError) {
    		System.out.println("Usage: COMMAND -f <path_of_file_to_send> -j <job_name> -ma <remote_master_ip> -mp <remote_master_port>");
    	}
    	
    	return !isError;
    }

	public static void main(String[] args) {
		
		if(!simpleCliParser(args)) {
			return;
		}
		
		try {
			Config.MY_IP = InetAddress.getLocalHost().toString().split("/")[1];
			if(!Utils.checkIP(Config.MY_IP)) {
				System.out.println("Unable to find host address");
				return;
			}
		} catch (UnknownHostException e) {
			//e.printStackTrace();
			System.out.println("Unable to find host address");
			return;
		}
		
		WCQueryClient client = new WCQueryClient();
		
		System.out.println("### Starting Client");
		
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
		ResultServer server = new ResultServer(portForThisJob);
		Thread tR = new Thread(server);
		tR.start();
		
		if(client.sendFile(Config.JOB_NAME, Config.FILE_PATH, Config.MY_IP, portForThisJob)) {
			System.out.println("File Sent with Success");
		} else {
			System.out.println("Master did not process my file...");
			server.stopMe();
			//tR.interrupt();
		}
		client.disconnect();
		return;
		
	}

}
