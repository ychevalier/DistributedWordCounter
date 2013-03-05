package Application;

import java.net.InetAddress;
import java.net.UnknownHostException;

import Network.FromMaster.PartServer;
import Network.ToMaster.Register;
import System.Config;
import System.Utils;


public class WCSlaveApp {
	
	static int mFileCounter = 0;

    public static synchronized int getNextFileCount() {
    	return mFileCounter++;
    }
    
    public static boolean amIReady() {
		return true;
	}
    
    static void initConfigPath() {
    	Config.JUNK_PATH = "slave_" + Config.SLAVE_PORT + "_tmp_files/";
    	Config.FILE_PATH = Config.JUNK_PATH + "files_to_process/";
    	Config.RESULT_PATH = Config.JUNK_PATH + "processed_files/";
    	
    }
    
    static boolean simpleCliParser(String [] args) {
    	
    	boolean isError = false;
    	
    	if(args.length != 6) {
    		isError = true;
    	} else {	
	    	for(int x = 0; x < args.length; x++){
	    	    if("-p".equals(args[x])){
	    	    	try {
	    	            Config.SLAVE_PORT = Integer.parseInt(args[x + 1]);
	    	            initConfigPath();
	    	            //Config.JUNK_PATH = "2";
	    	            //System.out.println("Oh");
	    	        } catch (NumberFormatException e) {
	    	            System.out.println("Local port must be an integer.");
	    	            isError = true;
	    	            break;
	    	        }
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
    		System.out.println("Usage: COMMAND -p <port_where_to_listen> -ma <remote_master_ip> -mp <remote_master_port>");
    	}
    	
    	return !isError;
    }
	
	public static void main(String[] args) {
		
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
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		    	Register reg = new Register();
				reg.connect(Config.MASTER_IP, Config.MASTER_PORT);
				if(!reg.unregister()) {
					System.out.println("Unable to unregister to the master.");
					return;
				} 
				reg.disconnect();
				System.out.println("Unregistered!");
		    }
		 });
		
		if(!simpleCliParser(args)) {
			return;
		}
		System.out.println("### Slave ###");
		Register reg = new Register();
		reg.connect(Config.MASTER_IP, Config.MASTER_PORT);
		if(!reg.register()) {
			System.out.println("Unable to register to the master.");
			return;
		} 
		reg.disconnect();
		
		PartServer server = new PartServer();
		server.launch();
	}

}
